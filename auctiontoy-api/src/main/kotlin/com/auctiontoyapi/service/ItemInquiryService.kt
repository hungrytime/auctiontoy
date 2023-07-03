package com.auctiontoyapi.service

import com.auctiontoyapi.adapter.`in`.common.page.PageContent
import com.auctiontoyapi.adapter.out.vo.ItemListVO
import com.auctiontoyapi.adapter.out.vo.ItemVO
import com.auctiontoyapi.adapter.out.vo.SellerItemDetailVO
import com.auctiontoyapi.adapter.out.vo.SellerItemListVO
import com.auctiontoyapi.application.port.`in`.FindItemUseCase
import com.auctiontoyapi.application.port.out.FindItemPort
import com.auctiontoydomain.entity.Item
import com.auctiontoydomain.entity.ItemStatus
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class ItemInquiryService(
    private val findItemPort: FindItemPort
) : FindItemUseCase {

    override fun findItemListForSeller(memberId: Long, pageable: Pageable): PageContent<List<SellerItemListVO>> {
        val itemInfo: Page<Item> = findItemPort.findItemListByMemberId(memberId, pageable)
        val itemList = itemInfo.content.map { SellerItemListVO.from(it) }
        return PageContent(itemInfo.number, itemInfo.totalPages, itemList)
    }

    override fun findItemDetailForSeller(itemId: Long): SellerItemDetailVO {
        return SellerItemDetailVO.from(findItemPort.findItemByItemId(itemId)!!)
    }


    override fun findItemList(memberId: Long, pageable: Pageable): PageContent<List<ItemListVO>> {
        val itemInfo: Page<Item> = findItemPort.findItemList(pageable)
        val itemList = transferItemToItemList(memberId, itemInfo)
        return PageContent(itemInfo.number, itemInfo.totalPages, itemList)
    }

    override fun findItemListByCreatedAt(
        memberId: Long,
        start: String,
        end: String,
        pageable: Pageable
    ): PageContent<List<ItemListVO>> {
        val itemInfo = findItemPort.findItemListByCreatedAt(start, end, pageable)
        val itemList = transferItemToItemList(memberId, itemInfo)
        return PageContent(itemInfo.number, itemInfo.totalPages, itemList)
    }

    override fun findItemListByStatus(memberId: Long, status: String, pageable: Pageable): PageContent<List<ItemListVO>> {
        val itemInfo = findItemPort.findItemListByStatus(status, pageable)
        val itemList = transferItemToItemList(memberId, itemInfo)
        return PageContent(itemInfo.number, itemInfo.totalPages, itemList)
    }

    override fun findBidItemListByMemberId(memberId: Long, pageable: Pageable): PageContent<List<ItemListVO>> {
        val itemInfo = findItemPort.findBidItemListByMemberId(memberId, pageable)
        val itemList = itemInfo.content.map { ItemListVO.from(it, true, it.bidMyPrice!!) }
        return PageContent(itemInfo.number, itemInfo.totalPages, itemList)
    }

    override fun findByItemId(itemId: String): ItemVO? {
        return findItemPort.findItemByItemId(itemId.toLong())?.let { ItemVO.from(it) }
    }

    override fun findByItemIdInRedis(itemId: String): ItemVO? {
        return findItemPort.findByItemIdInRedis(itemId)?.let { ItemVO.from(it) }
    }

    /**
     * 아이템 리스트를 가져와 내가 참여한 경매에 대하여 얼마를 배팅했는지 정보를 가져오는 함수
     * @param : 멤버 ID, itemList
     * @return : 내가 참여한 아이템의 정보가 기록된 리스트
     * */
    private fun transferItemToItemList(memberId: Long, item: Page<Item>): List<ItemListVO> {
        if (item.content.size == 0) return listOf()
        val bidItemInfos = HashMap<Long, BigDecimal>()

        val itemIds = item.content.map { it.itemId!! }
        val bidItems = findItemPort.findParticipationBid(memberId, itemIds)

        // 내가 참여한 경매에 대한 정보들을 미리 맵에 넣어둔다
        bidItems.map { bidItemInfos[it.itemId] = it.itemPrice }

        val itemList = item.content.map {
            // 경매 시작전이면 입찰한 사람과 가격이 존재할 수 있다.
            if (it.itemStatus == ItemStatus.PREPARE_AUCTION) {
                ItemListVO.from(it, false, BigDecimal.ZERO)
            }
            else {
                ItemListVO.from(it, bidItemInfos[it.itemId] != null, bidItemInfos[it.itemId] ?: BigDecimal.ZERO)
            }
        }
        return itemList
    }
}