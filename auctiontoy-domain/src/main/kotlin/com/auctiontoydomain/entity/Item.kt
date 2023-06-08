package com.auctiontoydomain.entity

import java.math.BigDecimal
import java.time.LocalDateTime

data class Item (
    val itemId: Long? = null,
    val memberId: Long,
    var name: String,
    var itemStatus: ItemStatus = ItemStatus.PREPARE_AUCTION,
    var basePrice: BigDecimal,
    var realTimePrice: BigDecimal,
    var desiredPrice: BigDecimal,
    val bidCount: Long,
    val totalBidAmount: BigDecimal,
    val highestBidMemberId: Long? = null,
    var auctionStartTime: LocalDateTime,
    var auctionEndTime: LocalDateTime
) {
    fun makeActiveStatus() { itemStatus = ItemStatus.ACTIVE_AUCTION }

    fun makeEndStatus() { itemStatus = ItemStatus.END_AUCTION }

    fun checkValidPrice(tryBidPrice: BigDecimal): Boolean {
        return tryBidPrice > realTimePrice
    }

    fun changeRealTimePrice(realTimePrice: BigDecimal) {
        this.realTimePrice = realTimePrice
    }

    fun modifyItem(
        itemName: String,
        basePrice: BigDecimal,
        desiredPrice: BigDecimal,
        auctionStartTime: LocalDateTime,
        auctionEndTime: LocalDateTime
    ) {
        this.name = itemName
        this.basePrice = basePrice
        this.desiredPrice = desiredPrice
        this.auctionStartTime = auctionStartTime
        this.auctionEndTime = auctionEndTime
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