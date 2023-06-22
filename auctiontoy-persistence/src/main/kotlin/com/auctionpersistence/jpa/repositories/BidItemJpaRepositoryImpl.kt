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
import java.math.BigDecimal

@Repository
class BidItemJpaRepositoryImpl : QuerydslRepositorySupport(BidItemJpaEntity::class.java), BidItemJpaRepositoryCustom {
    override fun findBidItemByMemberIdAndItemId(memberId: Long, itemId: Long): BigDecimal? {
        return from(bidItemJpaEntity)
            .where(bidItemJpaEntity.memberId.eq(memberId)
            .and(bidItemJpaEntity.itemId.eq(itemId)))
            .select(bidItemJpaEntity.itemPrice)
            .fetchFirst() ?: null
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
                    itemJpaEntity.highestBidMemberId,
                    itemJpaEntity.auctionStartTime,
                    itemJpaEntity.auctionEndTime
                )

            )
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetchResults()
            .let {
                PageImpl(it.results, pageable, it.total)
            }
    }
}