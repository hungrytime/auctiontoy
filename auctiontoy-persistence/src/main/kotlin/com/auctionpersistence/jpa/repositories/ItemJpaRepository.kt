package com.auctionpersistence.jpa.repositories

import com.auctionpersistence.jpa.entity.ItemJpaEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ItemJpaRepository: JpaRepository<ItemJpaEntity, Long>, ItemJpaRepositoryCustom

interface ItemJpaRepositoryCustom {
    fun findByMemberId(memberId: Long): List<ItemJpaEntity>
}