package com.auctiontoyapi.adapter.out.vo

import com.auctiontoydomain.entity.Item
import java.math.BigDecimal
import java.time.LocalDateTime

data class ItemVO(
    val itemId: Long? = null,
    val memberId: Long,
    val itemName: String,
    val itemStatus: String,
    val basePrice: BigDecimal,
    val minimumPrice: BigDecimal,
    val realTimePrice: BigDecimal,
    val desiredPrice: BigDecimal,
    val bidCount: Long,
    val highestBidMemberName: String? = null,
    val auctionStartTime: LocalDateTime,
    val auctionEndTime: LocalDateTime,
    val lastBidTime: LocalDateTime? = null
) {
    fun toItem(): Item {
        return Item.makeItem(
            memberId = memberId,
            itemName = itemName,
            basePrice = basePrice,
            desiredPrice = desiredPrice,
            minimumPrice = minimumPrice,
            auctionStartTime = auctionStartTime,
            auctionEndTime = auctionEndTime
        )
    }

//    fun toItem2(): Item {
//        return Item.makeItem2(
//            memberId = memberId,
//            itemName = itemName,
//            basePrice = basePrice,
//            desiredPrice = desiredPrice,
//            minimumPrice = minimumPrice,
//            auctionStartTime = auctionStartTime,
//            auctionEndTime = auctionEndTime
//        )
//    }

    companion object {
        fun from(item: Item) = ItemVO(
            itemId = item.itemId,
            memberId = item.memberId,
            highestBidMemberName = item.highestBidMemberName,
            itemName = item.name,
            itemStatus = item.itemStatus.toString(),
            basePrice = item.basePrice,
            realTimePrice = item.realTimePrice,
            desiredPrice = item.desiredPrice,
            minimumPrice = item.minimumPrice,
            auctionStartTime = item.auctionStartTime,
            auctionEndTime = item.auctionEndTime,
            bidCount = item.bidCount,
            lastBidTime = item.lastBidTime
        )
    }
}