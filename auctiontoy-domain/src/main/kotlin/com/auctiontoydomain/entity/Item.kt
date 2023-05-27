package com.auctiontoydomain.entity

import java.math.BigDecimal

data class Item (
    val itemId: Long? = null,
    val memberId: Long,
    val name: String,
    val basePrice: BigDecimal,
    val realTimePrice: BigDecimal,
    val desiredPrice: BigDecimal,
    val bidCount: Long,
    val totalBidAmount: BigDecimal,
    val highestBidMemberId: Long? = null
) {
    companion object {
        fun makeItem(
            memberId: Long,
            itemName: String,
            basePrice: BigDecimal,
            desiredPrice: BigDecimal,
            auctionStartTime: String,
            auctionEndTime: String
        ) = Item(
            memberId = memberId,
            name = itemName,
            basePrice = basePrice,
            realTimePrice = basePrice,
            desiredPrice = desiredPrice,
            bidCount = 0,
            totalBidAmount = BigDecimal.ZERO,
            highestBidMemberId = 0L
        )
    }
}

enum class ItemStatus {
    PREPARE_AUCTION,
    AUCTION,
    END_AUCTION
}