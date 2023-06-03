package com.auctiontoyapi.application.port.`in`

import com.auctiontoyapi.adapter.out.vo.ItemVO

interface FindItemListUseCase {
    fun findItemListByMemberId(memberId: Long) : List<ItemVO>
}