package com.auctiontoyapi.adapter.out.port

import com.auctionpersistence.jpa.repositories.ItemJpaRepository
import com.auctiontoyapi.application.port.out.FindItemPort
import com.auctiontoydomain.entity.Item
import org.springframework.stereotype.Component

@Component
class ItemInquiryAdapter(
    private val itemJpaRepository: ItemJpaRepository
) : FindItemPort {
    override fun findItemListByMemberId(memberId: Long): List<Item> {
        return itemJpaRepository.findByMemberId(memberId).map { it.to() }
    }
}