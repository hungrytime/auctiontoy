package com.auctiontoydomain.entity

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import java.math.BigDecimal
import java.time.LocalDateTime


data class Item (
    val itemId: Long? = null,
    val memberId: Long = 0L,
    var name: String = "",
    var itemStatus: ItemStatus = ItemStatus.PREPARE_AUCTION,
    var basePrice: BigDecimal = BigDecimal.ZERO,
    var realTimePrice: BigDecimal = BigDecimal.ZERO,
    var desiredPrice: BigDecimal = BigDecimal.ZERO,
    val bidCount: Long = 0L,
    var highestBidMemberId: Long? = null,
    @JsonDeserialize(using = LocalDateTimeDeserializer::class)
    @JsonSerialize(using = LocalDateTimeSerializer::class)
    var auctionStartTime: LocalDateTime = LocalDateTime.now(),
    @JsonDeserialize(using = LocalDateTimeDeserializer::class)
    @JsonSerialize(using = LocalDateTimeSerializer::class)
    var auctionEndTime: LocalDateTime = LocalDateTime.now()
) {
    constructor() : this(
        null,
        0L,
        "",
        ItemStatus.PREPARE_AUCTION,
        BigDecimal.ZERO,
        BigDecimal.ZERO,
        BigDecimal.ZERO,
        0L,
        null,
        LocalDateTime.now(),
        LocalDateTime.now()
    )

    fun makeActiveStatus() { itemStatus = ItemStatus.ACTIVE_AUCTION }

    fun makeEndStatus() { itemStatus = ItemStatus.END_AUCTION }

    fun checkValidPrice(tryBidPrice: BigDecimal): Boolean {
        return tryBidPrice > realTimePrice
    }

    fun changeBidItemInfo(realTimePrice: BigDecimal, highestBidMemberId: Long) {
        this.realTimePrice = realTimePrice
        this.highestBidMemberId = highestBidMemberId
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
            highestBidMemberId = 0L,
            auctionStartTime = auctionStartTime,
            auctionEndTime = auctionEndTime
        )

        fun makeItem2(
            memberId: Long,
            itemName: String,
            basePrice: BigDecimal,
            desiredPrice: BigDecimal,
            auctionStartTime: LocalDateTime,
            auctionEndTime: LocalDateTime
        ) = Item(
            itemId = 10,
            memberId = memberId,
            name = itemName,
            basePrice = basePrice,
            realTimePrice = basePrice,
            desiredPrice = desiredPrice,
            bidCount = 0,
            highestBidMemberId = 0L,
            auctionStartTime = auctionStartTime,
            auctionEndTime = auctionEndTime
        )
    }
}

enum class ItemStatus {
    PREPARE_AUCTION,
    ACTIVE_AUCTION,
    FAILED_AUCTION,
    END_AUCTION
}