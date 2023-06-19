package com.auctiontoyapi.application.port.`in`

import com.auctiontoyapi.adapter.out.vo.ItemVO

interface FindItemUseCase {
    fun findItemListByMemberId(memberId: Long) : List<ItemVO>
    fun findByItemId(itemId: String): ItemVO?
    fun findByItemIdInRedis(itemId: String): ItemVO?
}