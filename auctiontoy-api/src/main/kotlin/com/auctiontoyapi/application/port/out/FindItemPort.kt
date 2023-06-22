package com.auctiontoyapi.application.port.out

import com.auctiontoydomain.entity.Item
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.math.BigDecimal

interface FindItemPort {
    fun findItemList(pageable: Pageable): Page<Item>
    fun findParticipationBid(memberId: Long, itemId: Long): BigDecimal?
    fun findItemListByCreatedAt(start: String, end: String, pageable: Pageable): Page<Item>
    fun findItemListByStatus(status: String, pageable: Pageable): Page<Item>
    fun findItemListByMemberId(memberId: Long, pageable: Pageable): Page<Item>
    fun findByItemStatusAndStartDate(status: String): List<Item>
    fun findByItemStatusAndEndDate(status: String): List<Item>
    fun findItemByItemId(itemId: Long): Item?
    fun findItemByItemIdAndStatus(itemId: Long, status: String): Item?
    fun findByItemIdInRedis(key: String): Item?
}