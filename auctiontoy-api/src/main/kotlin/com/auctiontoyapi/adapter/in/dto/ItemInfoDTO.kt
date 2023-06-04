package com.auctiontoyapi.adapter.`in`.dto

import com.auctiontoyapi.adapter.out.vo.ItemVO
import java.math.BigDecimal
import java.time.LocalDateTime

data class ItemInfoDTO(
    val highestBidMemberId: Long?,
    val itemName: String,
    val basePrice: BigDecimal,
    val desiredPrice: BigDecimal,
    val auctionStartTime: LocalDateTime,
    val auctionEndTime: LocalDateTime
) {
    companion object {
        fun from(item: ItemVO) = ItemInfoDTO(
            highestBidMemberId = item.highestBidMemberId,
            itemName = item.itemName,
            basePrice = item.basePrice,
            desiredPrice = item.desiredPrice,
            auctionStartTime = item.auctionStartTime,
            auctionEndTime = item.auctionEndTime
        )
    }
}