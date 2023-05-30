package com.auctiontoydomain.entity

import java.math.BigDecimal
import java.time.LocalDateTime

data class Item (
    val itemId: Long? = null,
    val memberId: Long,
    val name: String,
    val basePrice: BigDecimal,
    val realTimePrice: BigDecimal,
    val desiredPrice: BigDecimal,
    val bidCount: Long,
    val totalBidAmount: BigDecimal,
    val highestBidMemberId: Long? = null,
    val auctionStartTime: LocalDateTime,
    val auctionEndTime: LocalDateTime
) {
    companion object {
        fun makeItem(
            memberId: Long,
            itemName: String,
            basePrice: BigDecimal,
            desiredPrice: BigDecimal,
            auctionStartTime: LocalDateTime,
            auctionEndTime: LocalDateTime
        ) = Item(
            memberId = memberId,
            name = itemName,
            basePrice = basePrice,
            realTimePrice = basePrice,
            desiredPrice = desiredPrice,
            bidCount = 0,
            totalBidAmount = BigDecimal.ZERO,
            highestBidMemberId = 0L,
            auctionStartTime = auctionStartTime,
            auctionEndTime = auctionEndTime
        )
    }
}

enum class ItemStatus {
    PREPARE_AUCTION,
    AUCTION,
    END_AUCTION
}