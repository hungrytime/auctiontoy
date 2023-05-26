package com.auctionpersistence.jpa.entity

import com.auctionpersistence.jpa.entity.base.BaseEntity
import java.math.BigDecimal
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
    val highestBidMemberId: Long? = null
) : BaseEntity()