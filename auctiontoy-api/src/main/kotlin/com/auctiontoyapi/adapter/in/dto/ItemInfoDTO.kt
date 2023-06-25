package com.auctiontoyapi.adapter.`in`.dto

import com.auctiontoyapi.adapter.out.vo.ItemVO
import java.math.BigDecimal
import java.time.LocalDateTime

data class ItemInfoDTO(
    val itemId: Long?,
    val itemName: String,
    val basePrice: BigDecimal,
    val desiredPrice: BigDecimal,
    val minimumPrice: BigDecimal,
    val auctionStartTime: LocalDateTime,
    val auctionEndTime: LocalDateTime,
    val itemStatus: String,
    val highestBidMemberName: String?,
    val realTimePrice: BigDecimal,
    val lastBidTime: LocalDateTime?
) {
    companion object {
        fun from(item: ItemVO) = ItemInfoDTO(
            highestBidMemberName = item.highestBidMemberName,
            itemName = item.itemName,
            basePrice = item.basePrice,
            desiredPrice = item.desiredPrice,
            auctionStartTime = item.auctionStartTime,
            auctionEndTime = item.auctionEndTime,
            itemId = item.itemId,
            itemStatus = item.itemStatus,
            lastBidTime = item.lastBidTime,
            minimumPrice = item.minimumPrice,
            realTimePrice = item.realTimePrice
        )
    }
}