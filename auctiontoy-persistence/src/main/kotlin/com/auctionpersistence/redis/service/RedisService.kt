package com.auctionpersistence.redis.service

import com.auctiontoydomain.entity.Item

interface RedisService {
    fun get(key: String): Any?
    fun getWithItem(key: String): Item?
    fun set(key: String, value: Any)
    fun setWithItem(key: String, item: Item)
    fun lock(key: String, lockName: String, lockTime: Long): Boolean?
    fun unlock(key: String): Boolean
}