package com.auctionpersistence.redis.service

interface RedisService {
    fun get(key: String): String?
    fun set(key: String, value: String)
    fun lock(key: String, lockName: String
    ): Boolean?
    fun unlock(key: String): Boolean
}