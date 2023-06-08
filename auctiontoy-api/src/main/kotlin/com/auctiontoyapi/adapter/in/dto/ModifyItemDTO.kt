package com.auctiontoyapi.adapter.`in`.dto

import com.auctiontoyapi.adapter.out.vo.ItemModifyVO
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class ModifyItemDTO(
    val itemId: Long,
    val itemName: String,
    val basePrice: BigDecimal,
    val desiredPrice: BigDecimal,
    val auctionStartTime: String,
    val auctionEndTime: String
) {
    fun toVO() = ItemModifyVO(
        itemId = itemId,
        itemName = itemName,
        basePrice = basePrice,
        desiredPrice = desiredPrice,
        auctionStartTime = LocalDateTime.parse(
            auctionStartTime,
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        ),
        auctionEndTime = LocalDateTime.parse(
            auctionEndTime,
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        )
    )
}