package com.auctionpersistence.redis.service

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import java.time.Duration

@Component
class RedisServiceImpl(
    private val redisTemplate: RedisTemplate<String, String>
): RedisService {
    override fun get(key: String): String? {
        val redis = redisTemplate.opsForValue()
        return redis.get(key)
    }

    override fun set(key: String, value: String) {
        val redis = redisTemplate.opsForValue()
        redis.set(key, value)
    }

    override fun lock(key: String, lockName: String): Boolean? {
        return redisTemplate.opsForValue().setIfAbsent(key, lockName, Duration.ofMillis(3_000))
    }

    override fun unlock(key: String): Boolean {
        return redisTemplate.delete(key)
    }
}