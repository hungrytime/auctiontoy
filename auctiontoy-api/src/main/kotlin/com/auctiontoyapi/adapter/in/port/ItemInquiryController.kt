package com.auctiontoyapi.adapter.`in`.port

import com.auctiontoyapi.adapter.`in`.common.dto.ResponseDTO
import com.auctiontoyapi.adapter.`in`.dto.ItemInfoDTO
import com.auctiontoyapi.application.port.`in`.FindItemListUseCase
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/item")
class ItemInquiryController(
    private val findItemListUseCase: FindItemListUseCase
) {
    @GetMapping("/item-info-list")
    fun getItemList(@RequestParam memberId: Long): ResponseDTO<List<ItemInfoDTO>> {
        return ResponseDTO.success(findItemListUseCase.findItemListByMemberId(memberId).map { ItemInfoDTO.from(it) })
    }

    @GetMapping("/redis")
    fun getRedis(@RequestParam key: String): ResponseDTO<String?> {
        return ResponseDTO.success(findItemListUseCase.testRedis(key))
    }
}