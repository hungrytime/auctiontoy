package com.auctiontoyapi.service

import com.auctiontoyapi.adapter.out.vo.ItemListVO
import com.auctiontoyapi.adapter.out.vo.ItemVO
import com.auctiontoyapi.application.port.`in`.FindItemUseCase
import com.auctiontoyapi.application.port.out.FindItemPort
import com.auctiontoydomain.entity.Item
import com.auctiontoydomain.entity.ItemStatus
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class ItemInquiryService(
    private val findItemPort: FindItemPort
) : FindItemUseCase {
    override fun findItemList(pageable: Pageable): Triple<List<ItemListVO>, Int, Int> {
        val itemInfo: Page<Item> = findItemPort.findItemList(pageable)
        val itemList = transferItemToItemList(itemInfo)
        return Triple(itemList, itemInfo.number, itemInfo.totalPages)
    }

    override fun findItemListByCreatedAt(start: String, end: String, pageable: Pageable): Triple<List<ItemListVO>, Int, Int> {
        val itemInfo = findItemPort.findItemListByCreatedAt(start, end, pageable)
        val itemList = transferItemToItemList(itemInfo)
        return Triple(itemList, itemInfo.number, itemInfo.totalPages)
    }

    override fun findItemListByStatus(status: String, pageable: Pageable): Triple<List<ItemListVO>, Int, Int> {
        val itemInfo = findItemPort.findItemListByStatus(status, pageable)
        val itemList = transferItemToItemList(itemInfo)
        return Triple(itemList, itemInfo.number, itemInfo.totalPages)
    }

    override fun findItemListByMemberId(memberId: Long, pageable: Pageable): Triple<List<ItemListVO>, Int, Int> {
        val itemInfo = findItemPort.findItemListByMemberId(memberId, pageable)
        val itemList = itemInfo.content.map { ItemListVO.from(it, true, it.bidMyPrice!!) }
        return Triple(itemList, itemInfo.number, itemInfo.totalPages)
    }

    override fun findByItemId(itemId: String): ItemVO? {
        return findItemPort.findItemByItemId(itemId.toLong())?.let { ItemVO.from(it) }
    }

    override fun findByItemIdInRedis(itemId: String): ItemVO? {
        return findItemPort.findByItemIdInRedis(itemId)?.let { ItemVO.from(it) }
    }

    private fun transferItemToItemList(item: Page<Item>):List<ItemListVO> {
        val itemList = item.content.map {
            if (it.itemStatus == ItemStatus.PREPARE_AUCTION) {
                ItemListVO.from(it, false, BigDecimal.ZERO)
            }
            else {
                val bestMyPrice: BigDecimal = findItemPort.findParticipationBid(it.memberId, it.itemId!!) ?: BigDecimal.ZERO
                // bestMyPrice가 없는 경우는 해당 경매에 참여 안한 경우
                ItemListVO.from(it, bestMyPrice != BigDecimal.ZERO, bestMyPrice)
            }
        }
        return itemList
    }
}