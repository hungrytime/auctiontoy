package com.auctiontoyapi.application.port.out

import com.auctiontoydomain.entity.Item

interface SaveItemPort {
    fun save(item: Item)
    fun saveAll(items: List<Item>)
}