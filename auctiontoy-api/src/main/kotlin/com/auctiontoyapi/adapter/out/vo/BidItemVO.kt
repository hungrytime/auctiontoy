package com.auctiontoyapi.adapter.out.vo

import java.math.BigDecimal

/**
 * 입찰 하기 위한 아이템 정보
 * */
data class BidItemVO(
    // 상품의 고유 ID
    val itemId: Long,
    // 입찰을 시도하는 사람 ID
    val memberId: Long,
    // 상품 가격
    val itemPrice: BigDecimal
)