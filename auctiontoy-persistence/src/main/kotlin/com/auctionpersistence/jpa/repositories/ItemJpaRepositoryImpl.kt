package com.auctionpersistence.jpa.repositories

import com.auctionpersistence.jpa.entity.ItemJpaEntity
import com.auctionpersistence.jpa.entity.QItemJpaEntity.itemJpaEntity
import com.auctiontoydomain.entity.ItemStatus
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class ItemJpaRepositoryImpl : QuerydslRepositorySupport(ItemJpaEntity::class.java), ItemJpaRepositoryCustom {
    override fun findAllWithPage(pageable: Pageable): Page<ItemJpaEntity> {
        return from(itemJpaEntity)
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetchResults()
            .let {
                PageImpl(it.results, pageable, it.total)
            }
    }

    override fun findByCreatedAt(start: LocalDateTime, end: LocalDateTime, pageable: Pageable): Page<ItemJpaEntity> {
        return from(itemJpaEntity)
            .where(itemJpaEntity.createdAt.goe(start)
                .and(itemJpaEntity.createdAt.loe(end)))
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetchResults()
            .let {
                PageImpl(it.results, pageable, it.total)
            }
    }

    override fun findByStatus(status: ItemStatus, pageable: Pageable): Page<ItemJpaEntity> {
        return from(itemJpaEntity)
            .where(itemJpaEntity.itemStatus.eq(status))
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetchResults()
            .let {
                PageImpl(it.results, pageable, it.total)
            }
    }

    override fun findByMemberId(memberId: Long): List<ItemJpaEntity> {
        return from(itemJpaEntity)
            .where(itemJpaEntity.memberId.eq(memberId))
            .fetch()
    }

    override fun findByIdAndStatus(id: Long, status: ItemStatus): ItemJpaEntity? {
        return from(itemJpaEntity)
            .where(itemJpaEntity.itemId.eq(id)
                .and(itemJpaEntity.itemStatus.eq(status)))
            .fetchFirst() ?: null
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