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
)

enum class ItemStatus {
    PREPARE_AUCTION,
    AUCTION,
    END_AUCTION
}