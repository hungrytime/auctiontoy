package com.auctiontoyapi.adapter.out.vo

import java.math.BigDecimal

data class BidItemVO(
    val itemId: Long,
    val memberId: Long,
    val itemPrice: BigDecimal
)