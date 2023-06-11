package com.auctiontoyapi.service

import com.auctiontoyapi.adapter.out.port.KafkaProducer
import com.auctiontoyapi.adapter.out.vo.BidItemVO
import com.auctiontoyapi.adapter.out.vo.ItemModifyVO
import com.auctiontoyapi.adapter.out.vo.ItemVO
import com.auctiontoyapi.application.port.`in`.BidItemUseCase
import com.auctiontoyapi.application.port.`in`.ModifyItemUseCase
import com.auctiontoyapi.application.port.`in`.RegisterItemUseCase
import com.auctiontoyapi.application.port.out.FindItemPort
import com.auctiontoyapi.application.port.out.SaveItemPort
import com.auctiontoydomain.entity.ItemStatus
import org.springframework.stereotype.Service


/**
 * 컨트롤러로부터 아이템을에 대한 정보를 받아 port에게 등록 혹은 수정을 하도록 하는 서비스
 * */
@Service
class ItemCommandService(
    private val registerItemPort: SaveItemPort,
    private val kafkaProducer: KafkaProducer,
    private val findItemPort: FindItemPort
): RegisterItemUseCase, BidItemUseCase, ModifyItemUseCase {

    /**
     * 아이템을 등록하는 함수
     * @param : 아이템을 등록하기 위한 정보
     * */
    override fun register(item: ItemVO) {
        registerItemPort.save(item.toItem())
    }

    override fun tryBid(msg: String) {
        kafkaProducer.sendMessage(msg)
    }

    /**
     * 입찰의 위하 아이템을 조회하고 문제가 없는 경우 아이템의 정보를 바꾸는 함수
     * @param : 입찰을 하기 위한 정보
     * */
    override fun bid(tryItem: BidItemVO) {
        // 입찰을 시도한 아이템이 현재 입찰이 가능한 상태인지 확인한다
        val item = findItemPort.findItemByItemId(tryItem.itemId)
            ?: throw Exception("존재하는 아이템 아이디가 없습니다 아이템 아이디 : ${tryItem.itemId}")
        // 입찰을 시도한 아이템은 ACTIVE 상태여야 합니다.
        require(item.itemStatus == ItemStatus.ACTIVE_AUCTION) { "경매중인 상품이 아닌 경우는 입찰을 진행할 수 없습니다." }
        // 만약 입찰을 시도한 아이템이 가격이 현재 가격보다 같거나 작을 경우는 데이터를 바꾸지 않는다
        if (item.checkValidPrice(tryItem.itemPrice).not()) return
        // 입찰가가 더 큰 경우는 item의 정보값을 바꾼다
        item.changeBidItemInfo(tryItem.itemPrice, tryItem.memberId)
        registerItemPort.save(item)
    }

    /**
     * 입찰하기전 상품의 정보를 바꿀 수 있는 함수
     * @param : 상품을 수정하기 위한 정보
     * */
    override fun modify(modifyItem: ItemModifyVO) {
        val item = findItemPort.findItemByItemId(modifyItem.itemId)
            ?: throw Exception("존재하는 아이템 아이디가 없습니다 아이템 아이디 : ${modifyItem.itemId}")

        require(item.itemStatus == ItemStatus.PREPARE_AUCTION) { "경매중이거나 종료가된 상품은 수정 할 수 없습니다" }

        item.modifyItem(
            modifyItem.itemName,
            modifyItem.basePrice,
            modifyItem.desiredPrice,
            modifyItem.auctionStartTime,
            modifyItem.auctionEndTime
        )

        registerItemPort.save(item)
    }

    companion object {
        const val ACTIVE_AUCTION = "ACTIVE_AUCTION"
    }
}