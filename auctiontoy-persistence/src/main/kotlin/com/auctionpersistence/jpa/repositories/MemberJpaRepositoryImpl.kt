package com.auctionpersistence.jpa.repositories

import com.auctionpersistence.jpa.entity.MemberJpaEntity
import com.auctionpersistence.jpa.entity.QMemberJpaEntity.memberJpaEntity
import com.auctiontoydomain.MemberStatus
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
class MemberJpaRepositoryImpl : QuerydslRepositorySupport(MemberJpaEntity::class.java), MemberJpaRepositoryCustom {
    override fun findByUserId(userId: String): MemberJpaEntity? {
        return from(memberJpaEntity)
            .where(memberJpaEntity.userId.eq(userId))
            .fetchFirst() ?: null
    }

    override fun findByUserIdAndStatus(userId: String, status: MemberStatus): MemberJpaEntity? {
        return from(memberJpaEntity)
            .where(memberJpaEntity.userId.eq(userId)
                .and(memberJpaEntity.memberStatus.eq(status)))
            .fetchFirst() ?: null
    }
}