package com.auctionpersistence.jpa.entity

import javax.persistence.*

@Entity
@Table(name = "end_bid")
class EndBidJpaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val endBidId: Long? = null,

    val memberId: Long = 0L,
    val itemId: Long = 0L,
    val description: String? = null
)