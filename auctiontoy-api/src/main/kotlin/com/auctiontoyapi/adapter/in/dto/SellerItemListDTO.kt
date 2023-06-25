package com.auctiontoyapi.adapter.`in`.dto

import com.auctiontoyapi.adapter.out.vo.SellerItemListVO

data class SellerItemListDTO(
    val itemId: Long,
    val itemName: String,
    val itemStatus: String
) {
    companion object {
        fun from(item: SellerItemListVO) = SellerItemListDTO(
            itemId = item.itemId,
            itemName = item.itemName,
            itemStatus = item.itemStatus
        )
    }
}
