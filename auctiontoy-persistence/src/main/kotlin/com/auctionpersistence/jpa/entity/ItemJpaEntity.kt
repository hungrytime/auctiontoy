package com.auctionpersistence.jpa.entity

import com.auctionpersistence.jpa.entity.base.BaseEntity
import com.auctiontoydomain.entity.Item
import java.math.BigDecimal
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "items")
class ItemJpaEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val itemId: Long? = null,

    // 상품을 올린 멤버ID
    val memberId: Long,
    val name: String,
    val basePrice: BigDecimal,
    val realTimePrice: BigDecimal,
    val desiredPrice: BigDecimal,
    val bidCount: Long,
    val totalBidAmount: BigDecimal,
    val highestBidMemberId: Long? = null,
    val auctionStartTime: LocalDateTime,
    val auctionEndTime: LocalDateTime
) : BaseEntity() {
    companion object {
        fun from(item: Item) = ItemJpaEntity(
            itemId = item.itemId,
            memberId = item.memberId,
            name = item.name,
            basePrice = item.basePrice,
            realTimePrice = item.realTimePrice,
            desiredPrice = item.desiredPrice,
            bidCount = item.bidCount,
            totalBidAmount = item.totalBidAmount,
            highestBidMemberId = item.highestBidMemberId,
            auctionStartTime = item.auctionStartTime,
            auctionEndTime = item.auctionEndTime
        )
    }
}