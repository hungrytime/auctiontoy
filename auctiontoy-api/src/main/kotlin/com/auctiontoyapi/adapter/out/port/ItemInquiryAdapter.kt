package com.auctiontoyapi.adapter.out.port

import com.auctionpersistence.jpa.repositories.ItemJpaRepository
import com.auctionpersistence.redis.service.RedisService
import com.auctiontoyapi.application.port.out.FindItemPort
import com.auctiontoydomain.entity.Item
import com.auctiontoydomain.entity.ItemStatus
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class ItemInquiryAdapter(
    private val itemJpaRepository: ItemJpaRepository,
    private val redisService: RedisService
) : FindItemPort {
    override fun findItemListByMemberId(memberId: Long): List<Item> {
        return itemJpaRepository.findByMemberId(memberId).map { it.to() }
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

    override fun testRedis(key: String): String? {
        return redisService.get(key)
    }
}