package com.auctiontoyapi.adapter.`in`.port

import com.auctiontoyapi.adapter.`in`.common.dto.PageResponseDTO
import com.auctiontoyapi.adapter.`in`.common.dto.ResponseDTO
import com.auctiontoyapi.adapter.`in`.common.page.PageParam
import com.auctiontoyapi.adapter.`in`.dto.ItemInfoDTO
import com.auctiontoyapi.adapter.`in`.dto.ItemListDTO
import com.auctiontoyapi.adapter.`in`.dto.SearchDateDTO
import com.auctiontoyapi.application.port.`in`.FindItemUseCase
import org.springframework.data.domain.PageRequest
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/item/find")
class ItemInquiryController(
    private val findItemUseCase: FindItemUseCase
) {
    /**
     * 상품의 기본 정보를 조회하는 메서드
     * @return : 상품의 정보
     * */
    @GetMapping("/info-list")
    fun getItemList(@ModelAttribute pageParam: PageParam): PageResponseDTO<List<ItemListDTO>> {
        val itemList = findItemUseCase.findItemList(PageRequest.of(pageParam.page, pageParam.size))
        return PageResponseDTO.success(itemList.first.map { ItemListDTO.from(it) }, itemList.second, itemList.third)
    }

    @GetMapping("/createdAt")
    fun getItemListByCreatedAt(@RequestBody date: SearchDateDTO, @ModelAttribute pageParam: PageParam): PageResponseDTO<List<ItemListDTO>> {
        val itemList = findItemUseCase.findItemListByCreatedAt(
            date.start,
            date.end,
            PageRequest.of(pageParam.page, pageParam.size)
        )

        return PageResponseDTO.success(itemList.first.map { ItemListDTO.from(it) }, itemList.second, itemList.third)
    }

    @GetMapping("/status")
    fun getItemListByCreatedAt(@RequestParam status: String, @ModelAttribute pageParam: PageParam): PageResponseDTO<List<ItemListDTO>> {
        val itemList = findItemUseCase.findItemListByStatus(status, PageRequest.of(pageParam.page, pageParam.size))

        return PageResponseDTO.success(itemList.first.map { ItemListDTO.from(it) }, itemList.second, itemList.third)
    }

    @GetMapping("/memberId")
    fun getItemListByMemberId(@RequestParam memberId: Long, @ModelAttribute pageParam: PageParam): PageResponseDTO<List<ItemListDTO>> {
        val itemList = findItemUseCase.findItemListByMemberId(memberId, PageRequest.of(pageParam.page, pageParam.size))

        return PageResponseDTO.success(itemList.first.map { ItemListDTO.from(it) }, itemList.second, itemList.third)
    }

    @GetMapping("/{itemId}")
    fun getRedis(@PathVariable itemId: String): ResponseDTO<ItemInfoDTO?> {
        return ResponseDTO.success(findItemUseCase.findByItemId(itemId)?.let { ItemInfoDTO.from(it)} )
    }
}