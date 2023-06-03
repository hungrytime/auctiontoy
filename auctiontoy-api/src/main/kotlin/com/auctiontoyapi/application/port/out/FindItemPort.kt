package com.auctiontoyapi.application.port.out

import com.auctiontoydomain.entity.Item

interface FindItemPort {
    fun findItemListByMemberId(memberId: Long): List<Item>
}