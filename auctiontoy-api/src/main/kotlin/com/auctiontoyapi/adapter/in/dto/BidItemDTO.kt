package com.auctiontoyapi.adapter.`in`.dto

import com.auctiontoyapi.adapter.out.vo.BidItemVO
import java.math.BigDecimal

data class BidItemDTO(
    val itemId: Long,
    val memberId: Long,
    val itemPrice: BigDecimal
) {
    fun to() = BidItemVO(
        itemId = itemId,
        memberId = memberId,
        itemPrice = itemPrice
    )
}