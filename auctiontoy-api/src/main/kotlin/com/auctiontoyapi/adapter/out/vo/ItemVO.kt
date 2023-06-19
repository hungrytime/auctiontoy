package com.auctiontoyapi.adapter.out.vo

import com.auctiontoydomain.entity.Item
import java.math.BigDecimal
import java.time.LocalDateTime

data class ItemVO(
    val memberId: Long,
    val highestBidMemberId: Long? = null,
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

    fun toItem2(): Item {
        return Item.makeItem2(
            memberId = memberId,
            itemName = itemName,
            basePrice = basePrice,
            desiredPrice = desiredPrice,
            auctionStartTime = auctionStartTime,
            auctionEndTime = auctionEndTime
        )
    }

    companion object {
        fun from(item: Item) = ItemVO(
            memberId = item.memberId,
            highestBidMemberId = item.highestBidMemberId,
            itemName = item.name,
            basePrice = item.basePrice,
            desiredPrice = item.desiredPrice,
            auctionStartTime = item.auctionStartTime,
            auctionEndTime = item.auctionEndTime
        )
    }
}