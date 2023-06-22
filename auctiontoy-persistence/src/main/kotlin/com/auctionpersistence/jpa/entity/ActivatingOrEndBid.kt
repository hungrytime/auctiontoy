package com.auctionpersistence.jpa.entity

import javax.persistence.*

@Entity
@Table(name = "activating_or_end_bid")
class ActivatingOrEndBid (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val activatingOrEndBidId: Long? = null,

    val memberId: Long = 0L,
    val itemId: Long = 0L
)