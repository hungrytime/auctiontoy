package com.auctiontoyapi.adapter.`in`.dto

import com.auctiontoyapi.adapter.out.vo.ItemVO
import com.auctiontoydomain.entity.ItemStatus
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class RegisterItemDTO(
    val memberUserId: String,
    val itemName: String,
    val basePrice: BigDecimal,
    val desiredPrice: BigDecimal,
    val minimumPrice: BigDecimal,
    val auctionStartTime: String,
    val auctionEndTime: String
) {
    fun toVO(memberId: Long) = ItemVO(
        memberId = memberId,
        itemName = itemName,
        itemStatus = ItemStatus.PREPARE_AUCTION.toString(),
        bidCount = 0L,
        realTimePrice = BigDecimal.ZERO,
        basePrice = basePrice,
        desiredPrice = desiredPrice,
        minimumPrice = minimumPrice,
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