package com.auctionpersistence.redis.configuration

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.serializer.StringRedisSerializer

import org.springframework.data.redis.core.RedisTemplate

import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory

import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories


@Configuration
@EnableRedisRepositories
class RedisConfig {
    @Value("\${redis.host}")
    private val host: String? = null

    @Value("\${redis.port}")
    private val port = 6379

    @Bean
    fun redisConnectionFactory(): RedisConnectionFactory? {
        return LettuceConnectionFactory(host!!, port)
    }

    @Bean
    fun redisTemplate(): RedisTemplate<*, *>? {
        val redisTemplate = RedisTemplate<ByteArray, ByteArray>()
        redisTemplate.setConnectionFactory(redisConnectionFactory()!!)
        redisTemplate.keySerializer = StringRedisSerializer()
        return redisTemplate
    }
}