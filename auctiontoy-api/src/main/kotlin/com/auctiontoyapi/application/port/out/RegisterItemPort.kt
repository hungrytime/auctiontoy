package com.auctiontoyapi.application.port.out

import com.auctiontoyapi.adapter.out.vo.ItemVO
import com.auctiontoydomain.entity.Item

interface RegisterItemPort {
    fun register(item: ItemVO)
    fun save(item: Item)
    fun saveAll(items: List<Item>)
}