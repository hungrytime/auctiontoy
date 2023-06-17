package com.auctiontoyapi.application.port.`in`

import com.auctiontoyapi.adapter.out.vo.BidItemVO

interface BidItemUseCase {
    fun tryBid(msg: String)
    fun bid(tryItem: BidItemVO)
    fun redisTest(key: String, value: String)
    fun lockTest(key: String)
}