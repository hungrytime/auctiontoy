package com.auctionpersistence.jpa.repositories

import com.auctionpersistence.jpa.entity.MemberJpaEntity
import com.auctionpersistence.jpa.entity.QMemberJpaEntity.memberJpaEntity
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
class MemberJpaRepositoryImpl : QuerydslRepositorySupport(MemberJpaEntity::class.java), MemberJpaRepositoryCustom {
    override fun findByUsername(username: String): MemberJpaEntity? {
        return from(memberJpaEntity)
            .where(memberJpaEntity.id.eq(username))
            .fetchFirst() ?: null
    }
}