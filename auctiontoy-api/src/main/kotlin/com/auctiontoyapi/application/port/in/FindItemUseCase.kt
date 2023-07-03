package com.auctiontoyapi.application.port.`in`

import com.auctiontoyapi.adapter.`in`.common.page.PageContent
import com.auctiontoyapi.adapter.out.vo.ItemListVO
import com.auctiontoyapi.adapter.out.vo.ItemVO
import com.auctiontoyapi.adapter.out.vo.SellerItemDetailVO
import com.auctiontoyapi.adapter.out.vo.SellerItemListVO
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface FindItemUseCase {
    fun findItemListForSeller(memberId: Long, pageable: Pageable): PageContent<List<SellerItemListVO>>
    fun findItemDetailForSeller(itemId: Long): SellerItemDetailVO

    fun findItemList(memberId: Long, pageable: Pageable) : PageContent<List<ItemListVO>>
    fun findItemListByCreatedAt(memberId: Long, start: String, end: String, pageable: Pageable) : PageContent<List<ItemListVO>>
    fun findItemListByStatus(memberId: Long, status: String, pageable: Pageable) : PageContent<List<ItemListVO>>
    fun findBidItemListByMemberId(memberId: Long, pageable: Pageable) : PageContent<List<ItemListVO>>
    fun findByItemId(itemId: String): ItemVO?
    fun findByItemIdInRedis(itemId: String): ItemVO?
}