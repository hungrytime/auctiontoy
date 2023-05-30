package com.auctionpersistence.jpa.repositories

import com.auctionpersistence.jpa.entity.MemberJpaEntity
import org.springframework.data.jpa.repository.JpaRepository

interface MemberJpaRepository: JpaRepository<MemberJpaEntity, Long>, MemberJpaRepositoryCustom

interface MemberJpaRepositoryCustom {
    fun findByUserId(userId: String): MemberJpaEntity?
}