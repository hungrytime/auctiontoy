package com.auctiontoyapi.application.port.out

import com.auctiontoyapi.adapter.out.vo.BidItemVO
import com.auctiontoydomain.entity.Item
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.math.BigDecimal
import java.time.LocalDateTime

interface FindItemPort {
    fun findItemList(pageable: Pageable): Page<Item>
    fun findItemListByMemberId(memberId: Long, pageable: Pageable): Page<Item>
    fun findParticipationBid(memberId: Long, itemIds: List<Long>): List<BidItemVO>
    fun findItemListByCreatedAt(start: String, end: String, pageable: Pageable): Page<Item>
    fun findItemListByStatus(status: String, pageable: Pageable): Page<Item>
    fun findBidItemListByMemberId(memberId: Long, pageable: Pageable): Page<Item>
    fun findBidItemTimeByItemId(itemId: Long): LocalDateTime?
    fun findByItemStatusAndStartDate(status: String): List<Item>
    fun findByItemStatusAndEndDate(status: String): List<Item>
    fun findItemByItemId(itemId: Long): Item?
    fun findItemByItemIdAndStatus(itemId: Long, status: String): Item?
    fun findByItemIdInRedis(key: String): Item?
}