package com.auctiontoyapi.adapter.out.port

import com.auctionpersistence.jpa.entity.BidItemJpaEntity
import com.auctionpersistence.jpa.entity.ItemJpaEntity
import com.auctionpersistence.jpa.repositories.BidItemJpaRepository
import com.auctionpersistence.jpa.repositories.ItemJpaRepository
import com.auctionpersistence.redis.service.RedisService
import com.auctiontoyapi.adapter.out.vo.ItemVO
import com.auctiontoyapi.application.port.out.SaveItemPort
import com.auctiontoydomain.entity.Item
import mu.KLogging
import org.springframework.stereotype.Component
import java.math.BigDecimal

/**
 * 서비스로부터 받은 아이템 정보를 데이터베이스에 반영하는 어댑터
 * */
@Component
class ItemCommandAdapter(
    private val itemJpaRepository: ItemJpaRepository,
    private val bidItemJpaRepository: BidItemJpaRepository,
    private val redisService: RedisService
): SaveItemPort {
    /**
     * 아이템을 저장하는 함수
     * @param : 아이템 정보
     * */
    override fun save(item: Item) {
        itemJpaRepository.save(ItemJpaEntity.from(item))
    }

    override fun saveBid(item: Item, itemPrice: BigDecimal) {
        bidItemJpaRepository.save(BidItemJpaEntity.from(item, itemPrice))
    }

    /**
     * 여러개의 아이템을 저장하는 함수
     * @param : 아이템 정보
     * */
    override fun saveAll(items: List<Item>) {
        val itemEntities = items.map {ItemJpaEntity.from(it)}
        itemJpaRepository.saveAll(itemEntities)
    }



    override fun saveRedisOnlyItem(key: String, item: Item) {
        redisService.setWithItem(key, item)
    }

    override fun saveRedis(key: String, value: String) {
        redisService.set(key, value)
    }

    companion object: KLogging()
}