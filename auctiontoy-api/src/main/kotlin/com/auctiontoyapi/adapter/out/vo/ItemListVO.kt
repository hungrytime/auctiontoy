package com.auctiontoyapi.adapter.out.vo

import com.auctiontoydomain.entity.Item
import java.math.BigDecimal

data class ItemListVO(
    val memberId: Long,
    val itemName: String,
    val isBidThisItem: Boolean = false,
    val itemStatus: String,
    val priceIBid: BigDecimal = BigDecimal.ZERO,
    val bestPrice: BigDecimal = BigDecimal.ZERO,
    val auctionStartTime: String,
    val auctionEndTime: String
) {
    companion object {
        fun from(item: Item, isBidThisItem: Boolean, priceIBid: BigDecimal) = ItemListVO(
            memberId = item.memberId,
            itemStatus = item.itemStatus.toString(),
            isBidThisItem = isBidThisItem,
            priceIBid = priceIBid,
            bestPrice = item.realTimePrice,
            itemName = item.name,
            auctionStartTime = item.auctionStartTime.toString(),
            auctionEndTime = item.auctionEndTime.toString()
        )
    }
}
