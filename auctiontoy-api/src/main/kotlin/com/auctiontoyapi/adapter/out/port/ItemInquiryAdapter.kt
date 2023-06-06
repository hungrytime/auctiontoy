package com.auctiontoyapi.adapter.out.port

import com.auctionpersistence.jpa.repositories.ItemJpaRepository
import com.auctiontoyapi.application.port.out.FindItemPort
import com.auctiontoyapi.common.toLocalDateTime
import com.auctiontoydomain.entity.Item
import com.auctiontoydomain.entity.ItemStatus
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Component
class ItemInquiryAdapter(
    private val itemJpaRepository: ItemJpaRepository
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
}