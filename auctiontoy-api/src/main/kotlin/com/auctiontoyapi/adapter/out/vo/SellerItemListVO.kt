package com.auctiontoyapi.adapter.out.vo

import com.auctiontoydomain.entity.Item

data class SellerItemListVO(
    val itemId: Long,
    val itemName: String,
    val itemStatus: String
) {
    companion object {
        fun from(item: Item) = SellerItemListVO(
            itemId = item.itemId!!,
            itemName = item.name,
            itemStatus = item.itemStatus.toString()
        )
    }
}
