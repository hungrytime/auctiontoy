package com.auctiontoyapi.adapter.out.port

import com.auctionpersistence.jpa.repositories.BidItemJpaRepository
import com.auctionpersistence.jpa.repositories.ItemJpaRepository
import com.auctionpersistence.redis.service.RedisService
import com.auctiontoyapi.adapter.out.vo.BidItemVO
import com.auctiontoyapi.application.port.out.FindItemPort
import com.auctiontoydomain.entity.Item
import com.auctiontoydomain.entity.ItemStatus
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Component
class ItemInquiryAdapter(
    private val itemJpaRepository: ItemJpaRepository,
    private val bidItemJpaRepository: BidItemJpaRepository,
    private val redisService: RedisService
) : FindItemPort {
    override fun findItemList(pageable: Pageable): Page<Item> {
        val entity =  itemJpaRepository.findAllWithPage(pageable)
        return entity.map { it.to() }
    }

    override fun findItemListByMemberId(memberId: Long, pageable: Pageable): Page<Item> {
        val entity =  itemJpaRepository.findItemByMemberIdWithPage(memberId, pageable)
        return entity.map { it.to() }
    }

    override fun findParticipationBid(memberId: Long, itemIds: List<Long>): List<BidItemVO> {
        return bidItemJpaRepository.findBidItemByMemberIdAndItemIds(memberId, itemIds).map { BidItemVO.from (it) }
    }

    override fun findItemListByCreatedAt(start: String, end: String, pageable: Pageable): Page<Item> {
        return itemJpaRepository.findByCreatedAt(
            LocalDateTime.parse(start, baseDateTimeFormatter),
            LocalDateTime.parse(end, baseDateTimeFormatter),
            pageable
        ).map { it.to() }
    }

    override fun findItemListByStatus(status: String, pageable: Pageable): Page<Item> {
        return itemJpaRepository.findByStatus(ItemStatus.valueOf(status), pageable).map { it.to() }
    }

    override fun findBidItemListByMemberId(memberId: Long, pageable: Pageable): Page<Item> {
        return bidItemJpaRepository.findBidItemByMemberId(memberId, pageable)
    }

    override fun findBidItemTimeByItemId(itemId: Long): LocalDateTime? {
        val isExist = bidItemJpaRepository.findBidItemsByItemId(itemId)
        if (isExist.isNotEmpty()) return bidItemJpaRepository.findBidItemByItemId(itemId).createdAt
        return null
    }

    override fun findByItemStatusAndStartDate(status: String): List<Item> {
        return itemJpaRepository.findByItemStatusAndStartDate(
            ItemStatus.valueOf(status),
            LocalDateTime.now()
        ).map { it.to() }
    }

    override fun findByItemStatusAndEndDate(status: String): List<Item> {
        return itemJpaRepository.findByItemStatusAndEndDate(
            ItemStatus.valueOf(status),
            LocalDateTime.now()
        ).map { it.to() }
    }

    override fun findItemByItemId(itemId: Long): Item? {
        val item = itemJpaRepository.findById(itemId)
        if (item.isPresent) return item.get().to()
        return null
    }

    override fun findItemByItemIdAndStatus(itemId: Long, status: String): Item? {
        return itemJpaRepository.findByIdAndStatus(itemId, ItemStatus.valueOf(status))?.to()
    }

    override fun findByItemIdInRedis(key: String): Item? {
        val result = redisService.getWithItem(key)
        return result
    }

    //TODO 이름 바꾸자
    companion object {
        val baseDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    }
}