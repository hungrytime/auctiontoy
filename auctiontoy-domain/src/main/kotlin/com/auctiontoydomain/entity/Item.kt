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
    var minimumPrice: BigDecimal = BigDecimal.ZERO,
    val bidMyPrice: BigDecimal? = null,
    var bidCount: Long = 0L,
    var highestBidMemberName: String? = null,
    @JsonDeserialize(using = LocalDateTimeDeserializer::class)
    @JsonSerialize(using = LocalDateTimeSerializer::class)
    var auctionStartTime: LocalDateTime = LocalDateTime.now(),
    @JsonDeserialize(using = LocalDateTimeDeserializer::class)
    @JsonSerialize(using = LocalDateTimeSerializer::class)
    var auctionEndTime: LocalDateTime = LocalDateTime.now(),
    val lastBidTime: LocalDateTime? = null
) {
    fun makeActiveStatus() { itemStatus = ItemStatus.ACTIVE_AUCTION }

    fun makeEndStatus() { itemStatus = ItemStatus.END_AUCTION }

    fun checkValidPrice(tryBidPrice: BigDecimal): Boolean {
        return tryBidPrice > realTimePrice
    }

    fun checkValidMember(memberId: Long): Boolean {
        return this.memberId != memberId
    }

    fun changeBidItemInfo(realTimePrice: BigDecimal, highestBidMemberName: String) {
        this.realTimePrice = realTimePrice
        this.highestBidMemberName = highestBidMemberName
        this.bidCount = this.bidCount + 1
    }

    fun modifyItem(
        itemName: String,
        basePrice: BigDecimal,
        desiredPrice: BigDecimal,
        minimumPrice: BigDecimal,
        auctionStartTime: LocalDateTime,
        auctionEndTime: LocalDateTime
    ) {
        this.name = itemName
        this.basePrice = basePrice
        this.desiredPrice = desiredPrice
        this.minimumPrice = minimumPrice
        this.auctionStartTime = auctionStartTime
        this.auctionEndTime = auctionEndTime
    }

    companion object {
        fun makeItem(
            memberId: Long,
            itemName: String,
            basePrice: BigDecimal,
            desiredPrice: BigDecimal,
            minimumPrice: BigDecimal,
            auctionStartTime: LocalDateTime,
            auctionEndTime: LocalDateTime
        ) = Item(
            memberId = memberId,
            name = itemName,
            basePrice = basePrice,
            realTimePrice = BigDecimal.ZERO,
            desiredPrice = desiredPrice,
            minimumPrice = minimumPrice,
            bidCount = 0,
            highestBidMemberName = "",
            auctionStartTime = auctionStartTime,
            auctionEndTime = auctionEndTime
        )

        fun makeItem2(
            memberId: Long,
            itemName: String,
            basePrice: BigDecimal,
            desiredPrice: BigDecimal,
            minimumPrice: BigDecimal,
            auctionStartTime: LocalDateTime,
            auctionEndTime: LocalDateTime
        ) = Item(
            itemId = 10,
            memberId = memberId,
            name = itemName,
            basePrice = basePrice,
            realTimePrice = BigDecimal.ZERO,
            desiredPrice = desiredPrice,
            minimumPrice = minimumPrice,
            bidCount = 0,
            highestBidMemberName = "",
            auctionStartTime = auctionStartTime,
            auctionEndTime = auctionEndTime
        )
    }
}

enum class ItemStatus {
    PREPARE_AUCTION,    // 경매 시작 전
    ACTIVE_AUCTION,     // 경매 중
    FAILED_AUCTION,     // 경매 실패
    END_AUCTION         // 경매 종료
}