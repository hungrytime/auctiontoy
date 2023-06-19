package com.auctiontoyapi.adapter.`in`.port

import com.auctiontoyapi.adapter.`in`.common.dto.ResponseDTO
import com.auctiontoyapi.adapter.`in`.dto.ItemInfoDTO
import com.auctiontoyapi.application.port.`in`.FindItemUseCase
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/item")
class ItemInquiryController(
    private val findItemUseCase: FindItemUseCase
) {
    @GetMapping("/item-info-list")
    fun getItemList(@RequestParam memberId: Long): ResponseDTO<List<ItemInfoDTO>> {
        return ResponseDTO.success(findItemUseCase.findItemListByMemberId(memberId).map { ItemInfoDTO.from(it) })
    }

    @GetMapping("/{itemId}")
    fun getRedis(@PathVariable itemId: String): ResponseDTO<ItemInfoDTO?> {
        return ResponseDTO.success(findItemUseCase.findByItemIdInRedis(itemId)?.let { ItemInfoDTO.from(it)} )
    }
}