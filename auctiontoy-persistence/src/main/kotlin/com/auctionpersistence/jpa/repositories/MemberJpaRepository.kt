package com.auctionpersistence.jpa.repositories

import com.auctionpersistence.jpa.entity.MemberJpaEntity
import org.springframework.data.jpa.repository.JpaRepository

interface MemberJpaRepository: JpaRepository<MemberJpaEntity, String>, MemberJpaRepositoryCustom {
}

interface MemberJpaRepositoryCustom {
    fun findByUsername(username: String): MemberJpaEntity?
}