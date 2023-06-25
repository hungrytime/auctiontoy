package com.auctiontoyapi.adapter.out.vo

import com.auctiontoydomain.entity.Item
import java.math.BigDecimal

data class SellerItemDetailVO(
    val itemId: Long,
    val itemName: String,
    val auctionStartTime: String,
    val auctionEndTime: String,
    val basePrice: BigDecimal,
    val desirePrice: BigDecimal,
    val realTimePrice: BigDecimal,
    val minimumPrice: BigDecimal,
    val itemStatus: String,
    val bidCount: Long,
    val highestBidMemberName: String?,
    val lastBidTime: String?
) {
    companion object {
        fun from(item: Item) = SellerItemDetailVO(
            itemId = item.itemId!!,
            itemName = item.name,
            auctionStartTime = item.auctionStartTime.toString(),
            auctionEndTime = item.auctionEndTime.toString(),
            basePrice = item.basePrice,
            desirePrice = item.desiredPrice,
            realTimePrice = item.realTimePrice,
            minimumPrice = item.minimumPrice,
            itemStatus = item.itemStatus.toString(),
            bidCount = item.bidCount,
            highestBidMemberName = item.highestBidMemberName,
            lastBidTime = item.lastBidTime?.toString()
        )
    }
}
