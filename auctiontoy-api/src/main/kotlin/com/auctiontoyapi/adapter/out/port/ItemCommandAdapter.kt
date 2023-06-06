package com.auctiontoyapi.adapter.out.port

import com.auctionpersistence.jpa.entity.ItemJpaEntity
import com.auctionpersistence.jpa.repositories.ItemJpaRepository
import com.auctiontoyapi.adapter.out.vo.ItemVO
import com.auctiontoyapi.application.port.out.RegisterItemPort
import com.auctiontoydomain.entity.Item
import org.springframework.stereotype.Component

@Component
class ItemCommandAdapter(
    private val itemJpaRepository: ItemJpaRepository
): RegisterItemPort {
    override fun register(item: ItemVO) {
        itemJpaRepository.save(ItemJpaEntity.from(item.toItem()))
    }

    override fun save(item: Item) {
        itemJpaRepository.save(ItemJpaEntity.from(item))
    }

    override fun saveAll(items: List<Item>) {
        val itemEntities = items.map {ItemJpaEntity.from(it)}
        itemJpaRepository.saveAll(itemEntities)
    }
}