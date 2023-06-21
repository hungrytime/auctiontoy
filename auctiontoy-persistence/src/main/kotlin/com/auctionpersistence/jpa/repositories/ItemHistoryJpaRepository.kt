package com.auctionpersistence.jpa.repositories

import com.auctionpersistence.jpa.entity.ItemHistoryJpaEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ItemHistoryJpaRepository : JpaRepository<ItemHistoryJpaEntity, Long>, ItemHistoryJpaRepositoryCustom

interface ItemHistoryJpaRepositoryCustom {

}