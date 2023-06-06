package com.auctionpersistence.jpa.repositories

import com.auctionpersistence.jpa.entity.ItemJpaEntity
import com.auctiontoydomain.entity.ItemStatus
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime

interface ItemJpaRepository: JpaRepository<ItemJpaEntity, Long>, ItemJpaRepositoryCustom

interface ItemJpaRepositoryCustom {
    fun findByMemberId(memberId: Long): List<ItemJpaEntity>

    fun findByItemStatusAndStartDate(status: ItemStatus, targetTime: LocalDateTime): List<ItemJpaEntity>
}