package com.auctiontoyapi.application.port.out

import com.auctiontoyapi.adapter.out.vo.ItemVO
import com.auctiontoydomain.entity.Item

interface SaveItemPort {
    fun save(item: Item)
    fun saveAll(items: List<Item>)
    fun saveRedisOnlyItem(key: String, item: Item)
    fun saveRedis(key:String, value: String)
//    fun lockTest(key: String): Boolean
//    fun unlockTest(key: String): Boolean
}