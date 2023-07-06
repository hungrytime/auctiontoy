package com.auctionpersistence.jpa.repositories

import com.auctionpersistence.jpa.entity.BidItemJpaEntity
import com.auctionpersistence.jpa.entity.QBidItemJpaEntity.bidItemJpaEntity
import com.auctionpersistence.jpa.entity.QItemJpaEntity.itemJpaEntity
import com.auctiontoydomain.entity.Item
import com.querydsl.core.types.Projections
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
class BidItemJpaRepositoryImpl : QuerydslRepositorySupport(BidItemJpaEntity::class.java), BidItemJpaRepositoryCustom {
    override fun findBidItemByMemberIdAndItemIds(memberId: Long, itemId: List<Long>): List<BidItemJpaEntity> {
        return from(bidItemJpaEntity)
            .where(bidItemJpaEntity.memberId.eq(memberId)
            .and(bidItemJpaEntity.itemId.`in`(itemId)))
            .fetch()
    }

    override fun findBidItemByMemberId(memberId: Long, pageable: Pageable): Page<Item> {
        return from(bidItemJpaEntity)
            .innerJoin(itemJpaEntity).on(itemJpaEntity.itemId.eq(bidItemJpaEntity.itemId))
            .where(bidItemJpaEntity.memberId.eq(memberId))
            .select(
                Projections.constructor(
                    Item::class.java,
                    itemJpaEntity.itemId,
                    itemJpaEntity.memberId,
                    itemJpaEntity.name,
                    itemJpaEntity.itemStatus,
                    itemJpaEntity.basePrice,
                    itemJpaEntity.realTimePrice,
                    itemJpaEntity.desiredPrice,
                    itemJpaEntity.minimumPrice,
                    bidItemJpaEntity.itemPrice,
                    itemJpaEntity.bidCount,
                    itemJpaEntity.highestBidMemberName,
                    itemJpaEntity.auctionStartTime,
                    itemJpaEntity.auctionEndTime,
                    bidItemJpaEntity.createdAt
                )

            )
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetchResults()
            .let {
                PageImpl(it.results, pageable, it.total)
            }
    }

    override fun findBidItemsByItemId(itemId: Long): List<BidItemJpaEntity> {
        return from(bidItemJpaEntity)
            .where(bidItemJpaEntity.itemId.eq(itemId))
            .fetch()
    }

    override fun findBidItemByItemId(itemId: Long): BidItemJpaEntity {
        return from(bidItemJpaEntity)
            .where(bidItemJpaEntity.itemId.eq(itemId))
            .orderBy(bidItemJpaEntity.bidItemId.desc())
            .fetchFirst()
    }
}