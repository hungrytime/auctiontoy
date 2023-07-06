package com.auctiontoyapi.service

import com.auctionpersistence.redis.lock.RedisLock
import com.auctiontoyapi.adapter.out.port.KafkaProducer
import com.auctiontoyapi.adapter.out.vo.BidItemVO
import com.auctiontoyapi.adapter.out.vo.ItemModifyVO
import com.auctiontoyapi.adapter.out.vo.ItemVO
import com.auctiontoyapi.application.port.`in`.BidItemUseCase
import com.auctiontoyapi.application.port.`in`.ModifyItemUseCase
import com.auctiontoyapi.application.port.`in`.RegisterItemUseCase
import com.auctiontoyapi.application.port.out.FindItemPort
import com.auctiontoyapi.application.port.out.FindMemberPort
import com.auctiontoyapi.application.port.out.SaveItemPort
import com.auctiontoydomain.entity.ItemStatus
import com.auctiontoydomain.exception.MemberException
import com.auctiontoydomain.exception.enum.ResultCode
import mu.KLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


/**
 * 컨트롤러로부터 아이템을에 대한 정보를 받아 port 에게 등록 혹은 수정을 하도록 하는 서비스
 * */
@Service
class ItemCommandService(
    private val saveItemPort: SaveItemPort,
    private val kafkaProducer: KafkaProducer,
    private val findItemPort: FindItemPort,
    private val findMemberPort: FindMemberPort
): RegisterItemUseCase, BidItemUseCase, ModifyItemUseCase {

    /**
     * 아이템을 등록하는 함수
     * @param : 아이템을 등록하기 위한 정보
     * */
    override fun register(item: ItemVO) {
        saveItemPort.save(item.toItem())
    }

    override fun tryBid(msg: BidItemVO) {
        kafkaProducer.sendMessage(msg)
    }

    /**
     * 입찰의 위해 아이템을 조회하고 문제가 없는 경우 아이템의 정보를 바꾸는 함수
     * @param : 입찰을 하기 위한 정보
     * */
    @Transactional
    override fun bid(tryItem: BidItemVO) {

        // 입찰을 시도한 아이템이 현재 입찰이 가능한 상태인지 확인한다
        val item = findItemPort.findItemByItemId(tryItem.itemId)
            ?: throw Exception("존재하는 아이템 아이디가 없습니다 아이템 아이디 : ${tryItem.itemId}")
        // 입찰을 시도하는 멤버가 존재하지 않는 경우
        require(findMemberPort.findByMemberId(tryItem.memberId) != null) {
            "멤버가 존재하지 않습니다 memberId : ${tryItem.memberId}"
        }
        // 입찰을 시도한 대상이 본인인 경우는 입찰을 할 수 없다
        if (item.checkValidMember(tryItem.memberId).not()) throw MemberException(
            ResultCode.MEMBER_INVALID,
            "자신의 경매품에는 입찰할 수 없습니다"
        )
        // 입찰을 시도한 아이템은 ACTIVE 상태여야 합니다.
        require(item.itemStatus == ItemStatus.ACTIVE_AUCTION) { "경매중인 상품이 아닌 경우는 입찰을 진행할 수 없습니다." }
        // 만약 입찰을 시도한 아이템이 가격이 현재 가격보다 같거나 작을 경우는 데이터를 바꾸지 않는다
        if (item.checkValidPrice(tryItem.itemPrice).not()) return

        // 입찰가가 더 큰 경우는 item의 정보값을 바꾼다
        val member = findMemberPort.findByMemberId(tryItem.memberId)

        item.changeBidItemInfo(tryItem.itemPrice, member!!.name)

        saveItemPort.save(item)
        saveItemPort.saveBid(item, tryItem.memberId, tryItem.itemPrice)
    }

//    override fun redisTest(item: ItemVO) {
//        val item2 = item.toItem2()
//        saveItemPort.saveRedisOnlyItem(item2.itemId!!.toString(), item2)
//    }

//    override fun redisTestString(value: String) {
//        saveItemPort.saveRedis("stringTest", value)
//    }

//    @RedisLock(key = "key", name = "redisLock")
//    override fun lockTest(key: String) {
//        logger.info("Process lock test $key")
//    }

    /**
     * 입찰하기전 상품의 정보를 바꿀 수 있는 함수
     * @param : 상품을 수정하기 위한 정보
     * */
    override fun modify(modifyItem: ItemModifyVO) {
        val item = findItemPort.findItemByItemId(modifyItem.itemId)

        require(item!!.itemStatus == ItemStatus.PREPARE_AUCTION) { "경매중이거나 종료가된 상품은 수정 할 수 없습니다" }

        item.modifyItem(
            modifyItem.itemName,
            modifyItem.basePrice,
            modifyItem.desiredPrice,
            modifyItem.minimumPrice,
            modifyItem.auctionStartTime,
            modifyItem.auctionEndTime
        )

        saveItemPort.save(item)
    }

    companion object: KLogging() {
        const val ACTIVE_AUCTION = "ACTIVE_AUCTION"
    }
}