package com.auctionpersistence.jpa.repositories

import com.auctionpersistence.jpa.entity.ItemJpaEntity
import com.auctiontoydomain.entity.ItemStatus
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime

interface ItemJpaRepository: JpaRepository<ItemJpaEntity, Long>, ItemJpaRepositoryCustom

interface ItemJpaRepositoryCustom {
    fun findAllWithPage(pageable: Pageable): Page<ItemJpaEntity>
    fun findByCreatedAt(start: LocalDateTime, end: LocalDateTime, pageable: Pageable): Page<ItemJpaEntity>
    fun findByStatus(status: ItemStatus, pageable: Pageable): Page<ItemJpaEntity>
    fun findByMemberId(memberId: Long): List<ItemJpaEntity>
    fun findByIdAndStatus(id: Long, status: ItemStatus): ItemJpaEntity?
    fun findByItemStatusAndStartDate(status: ItemStatus, targetTime: LocalDateTime): List<ItemJpaEntity>
    fun findByItemStatusAndEndDate(status: ItemStatus, targetTime: LocalDateTime): List<ItemJpaEntity>
}