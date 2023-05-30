package com.auctionpersistence.jpa.repositories

import com.auctionpersistence.jpa.entity.ItemJpaEntity
import com.auctionpersistence.jpa.entity.QItemJpaEntity.itemJpaEntity
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
class ItemJpaRepositoryImpl : QuerydslRepositorySupport(ItemJpaEntity::class.java), ItemJpaRepositoryCustom {
    override fun findByMemberId(memberId: Long): List<ItemJpaEntity> {
        return from(itemJpaEntity)
            .where(itemJpaEntity.memberId.eq(memberId))
            .fetch()
    }
}