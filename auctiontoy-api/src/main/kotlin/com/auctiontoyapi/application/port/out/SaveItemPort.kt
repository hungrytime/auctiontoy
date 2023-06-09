package com.auctiontoyapi.application.port.out

import com.auctiontoydomain.entity.Item
import java.math.BigDecimal

interface SaveItemPort {
    fun save(item: Item)
    fun saveBid(item: Item, memberId: Long, itemPrice: BigDecimal)
    fun saveAll(items: List<Item>)
    fun saveRedisOnlyItem(key: String, item: Item)
    fun saveRedis(key:String, value: String)
//    fun lockTest(key: String): Boolean
//    fun unlockTest(key: String): Boolean
}