package com.auctiontoyapi.adapter.`in`.dto

import com.auctiontoyapi.adapter.out.vo.ItemListVO
import com.auctiontoyapi.adapter.out.vo.ItemVO
import java.math.BigDecimal

data class ItemListDTO(
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
        fun from(item: ItemListVO) = ItemListDTO(
            memberId = item.memberId,
            itemName = item.itemName,
            itemStatus = item.itemStatus,
            isBidThisItem = item.isBidThisItem,
            priceIBid = item.priceIBid,
            bestPrice = item.bestPrice,
            auctionStartTime= item.auctionStartTime,
            auctionEndTime = item.auctionEndTime
        )
    }
}
