package com.auctionpersistence.redis.service

import com.auctiontoydomain.entity.Item
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.stereotype.Component
import java.time.Duration


@Component
class RedisServiceImpl(
    private val redisTemplate: RedisTemplate<Any, Any>
): RedisService {
    override fun get(key: String): Any? {
        val redis = redisTemplate.opsForValue()
        return redis.get(key)
    }

    override fun getWithItem(key: String): Item? {
        val mapper = jacksonObjectMapper()
        mapper.registerModule(JavaTimeModule())
//        redisTemplate.valueSerializer = Jackson2JsonRedisSerializer(Item::class.java)
        val redis = mapper.writeValueAsString(redisTemplate.opsForValue().get(key))
        return mapper.readValue(redis, Item::class.java)
    }

    override fun set(key: String, value: Any) {
        val redis = redisTemplate.opsForValue()
        redis.set(key, value)
    }

    override fun setWithItem(key: String, item: Item) {
//        redisTemplate.valueSerializer = Jackson2JsonRedisSerializer(Item::class.java)
        redisTemplate.opsForValue().set(key, item)
    }

    override fun lock(key: String, lockName: String, lockTime: Long): Boolean? {
        return redisTemplate.opsForValue().setIfAbsent(key, lockName, Duration.ofMillis(lockTime))
    }

    override fun unlock(key: String): Boolean {
        return redisTemplate.delete(key)
    }
}