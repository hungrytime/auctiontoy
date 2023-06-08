package com.auctiontoyapi.application.port.`in`

import com.auctiontoyapi.adapter.out.vo.ItemModifyVO

interface ModifyItemUseCase {
    fun modify(modifyItem: ItemModifyVO)
}