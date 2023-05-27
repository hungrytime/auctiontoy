package com.auctiontoyapi.adapter.`in`.dto

import com.auctiontoyapi.adapter.out.vo.ItemVO
import java.math.BigDecimal

data class RegisterItemDTO(
    val memberId: Long,
    val itemName: String,
    val basePrice: BigDecimal,
    val desiredPrice: BigDecimal,
    val auctionStartTime: String,
    val auctionEndTime: String
) {
    fun to() = ItemVO(
        memberId = memberId,
        itemName = itemName,
        basePrice = basePrice,
        desiredPrice = desiredPrice,
        auctionStartTime = auctionStartTime,
        auctionEndTime = auctionEndTime
    )
}