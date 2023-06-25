package com.auctiontoyapi.adapter.out.vo

import java.math.BigDecimal
import java.time.LocalDateTime

data class ItemModifyVO(
    val itemId: Long,
    val itemName: String,
    val basePrice: BigDecimal,
    val desiredPrice: BigDecimal,
    val minimumPrice: BigDecimal,
    val auctionStartTime: LocalDateTime,
    val auctionEndTime: LocalDateTime
) {
}