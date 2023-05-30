package com.auctiontoyapi.adapter.out.vo

import com.auctiontoydomain.entity.Item
import java.math.BigDecimal
import java.time.LocalDateTime

data class ItemVO(
    val memberId: Long,
    val itemName: String,
    val basePrice: BigDecimal,
    val desiredPrice: BigDecimal,
    val auctionStartTime: LocalDateTime,
    val auctionEndTime: LocalDateTime
) {
    fun toItem(): Item {
        return Item.makeItem(
            memberId = memberId,
            itemName = itemName,
            basePrice = basePrice,
            desiredPrice = desiredPrice,
            auctionStartTime = auctionStartTime,
            auctionEndTime = auctionEndTime
        )
    }
}