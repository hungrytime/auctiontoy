package com.auctionpersistence.jpa.repositories

import com.auctionpersistence.jpa.entity.EndBidJpaEntity
import org.springframework.data.jpa.repository.JpaRepository

interface EndBidJpaRepository : JpaRepository<EndBidJpaEntity, Long>, EndBidJpaRepositoryCustom

interface EndBidJpaRepositoryCustom {

}