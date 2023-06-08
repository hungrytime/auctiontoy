package com.auctiontoyapi.service

import com.auctiontoyapi.adapter.out.port.KafkaProducer
import com.auctiontoyapi.adapter.out.vo.BidItemVO
import com.auctiontoyapi.adapter.out.vo.ItemModifyVO
import com.auctiontoyapi.adapter.out.vo.ItemVO
import com.auctiontoyapi.application.port.`in`.BidItemUseCase
import com.auctiontoyapi.application.port.`in`.ModifyItemUseCase
import com.auctiontoyapi.application.port.`in`.RegisterItemUseCase
import com.auctiontoyapi.application.port.out.FindItemPort
import com.auctiontoyapi.application.port.out.RegisterItemPort
import com.auctiontoydomain.entity.ItemStatus
import org.springframework.stereotype.Service

@Service
class ItemCommandService(
    private val registerItemPort: RegisterItemPort,
    private val kafkaProducer: KafkaProducer,
    private val findItemPort: FindItemPort
): RegisterItemUseCase, BidItemUseCase, ModifyItemUseCase {
    override fun register(item: ItemVO) {
        registerItemPort.register(item)
    }

    override fun tryBid(msg: String) {
        kafkaProducer.sendMessage(msg)
    }

    override fun bid(tryItem: BidItemVO) {
        val item = findItemPort.findItemByItemId(tryItem.itemId) ?: throw Exception("존재하는 아이템 아이디가 없습니다 아이템 아이디 : ${tryItem.itemId}")
        if (item.checkValidPrice(tryItem.itemPrice).not()) return
        item.changeRealTimePrice(tryItem.itemPrice)
        registerItemPort.save(item)
    }

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
}