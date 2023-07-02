package com.auctiontoyapi.application.port.`in`

import com.auctiontoyapi.adapter.out.vo.BidItemVO
import com.auctiontoyapi.adapter.out.vo.ItemVO

interface BidItemUseCase {
    fun tryBid(msg: BidItemVO)
    fun bid(tryItem: BidItemVO)
//    fun redisTest(item: ItemVO)
    fun redisTestString(value: String)
    fun lockTest(key: String)
}