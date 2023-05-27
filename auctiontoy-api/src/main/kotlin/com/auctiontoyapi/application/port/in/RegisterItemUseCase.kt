package com.auctiontoyapi.application.port.`in`

import com.auctiontoyapi.adapter.out.vo.ItemVO

interface RegisterItemUseCase {
    fun register(item: ItemVO)
}