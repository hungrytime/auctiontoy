package com.auctiontoyapi.application.port.`in`

import com.auctiontoyapi.adapter.out.vo.ItemListVO
import com.auctiontoyapi.adapter.out.vo.ItemVO
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface FindItemUseCase {
    fun findItemList(pageable: Pageable) : Triple<List<ItemListVO>, Int, Int>
    fun findItemListByCreatedAt(start: String, end: String, pageable: Pageable) : Triple<List<ItemListVO>, Int, Int>
    fun findItemListByStatus(status: String, pageable: Pageable) : Triple<List<ItemListVO>, Int, Int>
    fun findItemListByMemberId(memberId: Long, pageable: Pageable) : Triple<List<ItemListVO>, Int, Int>
    fun findByItemId(itemId: String): ItemVO?
    fun findByItemIdInRedis(itemId: String): ItemVO?
}