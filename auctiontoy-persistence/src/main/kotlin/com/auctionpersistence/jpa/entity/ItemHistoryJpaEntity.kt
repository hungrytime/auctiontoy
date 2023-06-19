package com.auctionpersistence.jpa.entity

import com.auctionpersistence.jpa.entity.base.BaseEntity
import java.math.BigDecimal
import javax.persistence.*

@Entity
@Table(name = "item_history")
class ItemHistoryJpaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val itemHistoryId: Long? = null,

    val itemId: Long = 0L,
    val memberId: Long = 0L,
    val itemPrice: BigDecimal = BigDecimal.ZERO
): BaseEntity()