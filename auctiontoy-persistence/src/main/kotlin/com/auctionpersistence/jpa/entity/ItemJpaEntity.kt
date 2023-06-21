package com.auctionpersistence.jpa.entity

import com.auctionpersistence.jpa.entity.base.BaseEntity
import com.auctiontoydomain.MemberStatus
import com.auctiontoydomain.entity.Item
import com.auctiontoydomain.entity.ItemStatus
import java.math.BigDecimal
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "items")
class ItemJpaEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val itemId: Long? = null,

    // 상품을 올린 멤버ID
    val memberId: Long,
    // 상품 이름
    val name: String,
    // 상품 상태
    @Enumerated(EnumType.STRING)
    val itemStatus: ItemStatus = ItemStatus.PREPARE_AUCTION,
    // 경매 시작 금액
    val basePrice: BigDecimal,
    // 실시간 경매 금액
    val realTimePrice: BigDecimal,
    // 최대 경매 금액 (해당 금액과 같거나 더 클 경우 경매 종료)
    val desiredPrice: BigDecimal,
    // 최소 경매 금액 (해당 금액에 도달 못할 시 경매 실패)
    val minimumPrice: BigDecimal,
    // 경매 횟수
    val bidCount: Long,
    // 최대 입찰 금액의 멤버 ID
    val highestBidMemberId: Long? = null,
    val auctionStartTime: LocalDateTime,
    val auctionEndTime: LocalDateTime,

    // 조회 시 History 가 같이 조회가 자주 되기 떄문에 column 에 포함 안되게 Mapping 만 하기 위해서
    // insertable, updatable false 로 설정하여 매핑
    @OneToMany
    @JoinColumn(name = "itemId", insertable = false, updatable = false)
    val itemHistoryJpaEntitySet: Set<ItemHistoryJpaEntity> = setOf()

) : BaseEntity() {

    fun to() = Item(
        itemId = this.itemId,
        memberId = this.memberId,
        name = this.name,
        basePrice = this.basePrice,
        realTimePrice = this.realTimePrice,
        desiredPrice = this.desiredPrice,
        minimumPrice = this.minimumPrice,
        bidCount = this.bidCount,
        highestBidMemberId = this.highestBidMemberId,
        auctionStartTime = this.auctionStartTime,
        auctionEndTime = this.auctionEndTime
    )

    companion object {
        fun from(item: Item) = ItemJpaEntity(
            itemId = item.itemId,
            memberId = item.memberId,
            name = item.name,
            basePrice = item.basePrice,
            realTimePrice = item.realTimePrice,
            desiredPrice = item.desiredPrice,
            minimumPrice = item.minimumPrice,
            bidCount = item.bidCount,
            highestBidMemberId = item.highestBidMemberId,
            auctionStartTime = item.auctionStartTime,
            auctionEndTime = item.auctionEndTime
        )
    }
}