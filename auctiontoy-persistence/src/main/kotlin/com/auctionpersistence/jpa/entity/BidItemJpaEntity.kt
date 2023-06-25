package com.auctionpersistence.jpa.entity

import com.auctionpersistence.jpa.entity.base.BaseEntity
import com.auctiontoydomain.entity.Item
import java.math.BigDecimal
import javax.persistence.*

@Entity
@Table(name = "bid_items")
class BidItemJpaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val bidItemId: Long? = null,

    val itemId: Long = 0L,
    val memberId: Long = 0L,
    val itemPrice: BigDecimal = BigDecimal.ZERO
): BaseEntity() {
    companion object {
        fun from(item: Item, itemPrice: BigDecimal) = BidItemJpaEntity(
            itemId = item.itemId!!,
            memberId = item.memberId,
            itemPrice = itemPrice
        )
    }
}