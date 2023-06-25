package com.auctionpersistence.jpa.repositories

import com.auctionpersistence.jpa.entity.BidItemJpaEntity
import com.auctiontoydomain.entity.Item
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface BidItemJpaRepository : JpaRepository<BidItemJpaEntity, Long>, BidItemJpaRepositoryCustom

interface BidItemJpaRepositoryCustom {
    fun findBidItemByMemberIdAndItemIds(memberId: Long, itemId: List<Long>): List<BidItemJpaEntity>
    fun findBidItemByMemberId(memberId: Long, pageable: Pageable): Page<Item>
}