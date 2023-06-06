package com.auctiontoyapi.application.port.`in`

import com.auctiontoyapi.adapter.out.vo.BidItemVO

interface BidItemUseCase {
    fun tryBid(msg: String)
    fun bid(item: BidItemVO)
}