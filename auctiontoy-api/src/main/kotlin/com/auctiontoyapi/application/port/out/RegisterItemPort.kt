package com.auctiontoyapi.application.port.out

import com.auctiontoyapi.adapter.out.vo.ItemVO

interface RegisterItemPort {
    fun register(item: ItemVO)
}