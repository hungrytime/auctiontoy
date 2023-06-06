package com.auctionpersistence.jpa.repositories

import com.auctionpersistence.jpa.entity.ItemJpaEntity
import com.auctionpersistence.jpa.entity.QItemJpaEntity.itemJpaEntity
import com.auctiontoydomain.entity.ItemStatus
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class ItemJpaRepositoryImpl : QuerydslRepositorySupport(ItemJpaEntity::class.java), ItemJpaRepositoryCustom {
    override fun findByMemberId(memberId: Long): List<ItemJpaEntity> {
        return from(itemJpaEntity)
            .where(itemJpaEntity.memberId.eq(memberId))
            .fetch()
    }

    override fun findByItemStatusAndStartDate(status: ItemStatus, targetTime: LocalDateTime): List<ItemJpaEntity> {
        return from(itemJpaEntity)
            .where(itemJpaEntity.auctionStartTime.loe(targetTime)
                .and(itemJpaEntity.itemStatus.eq(status)))
                .fetch()
    }

    override fun findByItemStatusAndEndDate(status: ItemStatus, targetTime: LocalDateTime): List<ItemJpaEntity> {
        return from(itemJpaEntity)
            .where(itemJpaEntity.auctionEndTime.loe(targetTime)
                .and(itemJpaEntity.itemStatus.eq(status)))
            .fetch()
    }
}