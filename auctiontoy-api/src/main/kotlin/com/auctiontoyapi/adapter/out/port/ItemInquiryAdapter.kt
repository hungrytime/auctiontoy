package com.auctiontoyapi.adapter.out.port

import com.auctionpersistence.jpa.repositories.BidItemJpaRepository
import com.auctionpersistence.jpa.repositories.ItemJpaRepository
import com.auctionpersistence.redis.service.RedisService
import com.auctiontoyapi.application.port.out.FindItemPort
import com.auctiontoyapi.common.toLocalDateTime
import com.auctiontoydomain.entity.Item
import com.auctiontoydomain.entity.ItemStatus
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.time.LocalDateTime

@Component
class ItemInquiryAdapter(
    private val itemJpaRepository: ItemJpaRepository,
    private val bidItemJpaRepository: BidItemJpaRepository,
    private val redisService: RedisService
) : FindItemPort {
    override fun findItemList(pageable: Pageable): Page<Item> {
        return itemJpaRepository.findAllWithPage(pageable).map { it.to() }
    }

    override fun findParticipationBid(memberId: Long, itemId: Long): BigDecimal? {
        return bidItemJpaRepository.findBidItemByMemberIdAndItemId(memberId, itemId)
    }

    override fun findItemListByCreatedAt(start: String, end: String, pageable: Pageable): Page<Item> {
        return itemJpaRepository.findByCreatedAt(start.toLocalDateTime(), end.toLocalDateTime(), pageable).map { it.to() }
    }

    override fun findItemListByStatus(status: String, pageable: Pageable): Page<Item> {
        return itemJpaRepository.findByStatus(ItemStatus.valueOf(status), pageable).map { it.to() }
    }

    override fun findItemListByMemberId(memberId: Long, pageable: Pageable): Page<Item> {
        return bidItemJpaRepository.findBidItemByMemberId(memberId, pageable)
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
}