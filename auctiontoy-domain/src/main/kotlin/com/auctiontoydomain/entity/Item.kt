package com.auctiontoydomain.entity

import java.math.BigDecimal
import java.time.LocalDateTime

data class Item (
    val itemId: Long? = null,
    val memberId: Long,
    val name: String,
    var itemStatus: ItemStatus = ItemStatus.PREPARE_AUCTION,
    val basePrice: BigDecimal,
    var realTimePrice: BigDecimal,
    val desiredPrice: BigDecimal,
    val bidCount: Long,
    val totalBidAmount: BigDecimal,
    val highestBidMemberId: Long? = null,
    val auctionStartTime: LocalDateTime,
    val auctionEndTime: LocalDateTime
) {
    fun makeActiveStatus() { itemStatus = ItemStatus.ACTIVE_AUCTION }

    fun makeEndStatus() { itemStatus = ItemStatus.END_AUCTION }

    fun checkValidPrice(tryBidPrice: BigDecimal): Boolean {
        return tryBidPrice > realTimePrice
    }

    fun changeRealTimePrice(realTimePrice: BigDecimal) {
        this.realTimePrice = realTimePrice
    }

    companion object {
        fun makeItem(
            memberId: Long,
            itemName: String,
            basePrice: BigDecimal,
            desiredPrice: BigDecimal,
            auctionStartTime: LocalDateTime,
            auctionEndTime: LocalDateTime
        ) = Item(
            memberId = memberId,
            name = itemName,
            basePrice = basePrice,
            realTimePrice = basePrice,
            desiredPrice = desiredPrice,
            bidCount = 0,
            totalBidAmount = BigDecimal.ZERO,
            highestBidMemberId = 0L,
            auctionStartTime = auctionStartTime,
            auctionEndTime = auctionEndTime
        )
    }
}

enum class ItemStatus {
    PREPARE_AUCTION,
    ACTIVE_AUCTION,
    END_AUCTION
}