package com.auctiontoyapi.adapter.`in`.dto

import com.auctiontoyapi.adapter.out.vo.SellerItemDetailVO
import java.math.BigDecimal

data class SellerItemDetailDTO(
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
        fun from(item: SellerItemDetailVO) = SellerItemDetailDTO(
            itemId = item.itemId,
            itemName = item.itemName,
            auctionStartTime = item.auctionStartTime,
            auctionEndTime = item.auctionEndTime,
            basePrice = item.basePrice,
            desirePrice = item.desirePrice,
            realTimePrice = item.realTimePrice,
            minimumPrice = item.minimumPrice,
            itemStatus = item.itemStatus,
            bidCount = item.bidCount,
            highestBidMemberName = item.highestBidMemberName,
            lastBidTime = item.lastBidTime
        )
    }
}
