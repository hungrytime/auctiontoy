package com.auctiontoyapi.adapter.`in`.port

import com.auctiontoyapi.adapter.`in`.common.dto.PageResponseDTO
import com.auctiontoyapi.adapter.`in`.common.dto.ResponseDTO
import com.auctiontoyapi.adapter.`in`.common.page.PageParam
import com.auctiontoyapi.adapter.`in`.dto.*
import com.auctiontoyapi.application.port.`in`.FindItemUseCase
import org.springframework.data.domain.PageRequest
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/item/find")
class ItemInquiryController(
    private val findItemUseCase: FindItemUseCase
) {

    /**
     * 판매자 뷰에서 상품 리스트의 기본 정보를 조회하는 메서드
     * @param : 멤버 아이디
     * @return : 상품의 정보
     * */
    @GetMapping("/seller")
    fun getSellerItemList(
        @RequestParam memberId: Long,
        @ModelAttribute pageParam: PageParam
    ): PageResponseDTO<List<SellerItemListDTO>> {
        val itemList = findItemUseCase.findItemListForSeller(memberId, PageRequest.of(pageParam.page, pageParam.size))
        return PageResponseDTO.success(itemList.contents.map { SellerItemListDTO.from(it) }, itemList.page, itemList.totalPage)
    }

    /**
     * 판매자 뷰에서 상품의 기본 정보를 조회하는 메서드
     * @param : 상품 아이디
     * @return : 상품의 정보
     * */
    @GetMapping("/seller/detail/{itemId}")
    fun getSellerItemDetail(
        @PathVariable itemId: Long
    ): ResponseDTO<SellerItemDetailDTO> {
        return ResponseDTO.success(SellerItemDetailDTO.from(findItemUseCase.findItemDetailForSeller(itemId)))
    }



    /**
     * 참가자 뷰에서 상품의 기본 정보를 조회하는 메서드
     * @return : 상품의 정보
     * */
    @GetMapping("/participant")
    fun getItemList(@RequestParam memberId: Long, @ModelAttribute pageParam: PageParam): PageResponseDTO<List<ItemListDTO>> {
        val itemList = findItemUseCase.findItemList(memberId, PageRequest.of(pageParam.page, pageParam.size))
        return PageResponseDTO.success(itemList.contents.map { ItemListDTO.from(it) }, itemList.page, itemList.totalPage)
    }

    /**
     * 참가자 뷰에서 상품 등록 날짜를 기준으로 검색하는 메서드
     * @param : page 정보와 검색할 날짜의 사작과 끝을 받는다
     * @return : 상품의 정보
     * */
    @GetMapping("/participant/createdAt")
    fun getItemListByCreatedAt(@RequestBody form: SearchDateWithPageDTO): PageResponseDTO<List<ItemListDTO>> {
        val itemList = findItemUseCase.findItemListByCreatedAt(
            form.memberId,
            form.startDate,
            form.endDate,
            PageRequest.of(form.page, form.size)
        )

        return PageResponseDTO.success(itemList.contents.map { ItemListDTO.from(it) }, itemList.page, itemList.totalPage)
    }

    /**
     * 상품의 상태에 따라 검색하는 메서드
     * @param : page 정보와 상품의 상태를 받는다
     * @return : 상품의 정보
     * */
    @GetMapping("/participant/status")
    fun getItemListByCreatedAt(@RequestParam memberId: Long, @RequestParam status: String, @ModelAttribute pageParam: PageParam): PageResponseDTO<List<ItemListDTO>> {
        val itemList =
            findItemUseCase.findItemListByStatus(memberId, status, PageRequest.of(pageParam.page, pageParam.size))

        return PageResponseDTO.success(itemList.contents.map { ItemListDTO.from(it) }, itemList.page, itemList.totalPage)
    }

    /**
     * 내가 참여중인 경매에 대한 정보
     * @param : 페이지 정보와 멤버 아이디
     * @return : 상품의 정보
     * */
    @GetMapping("/participanting")
    fun getItemListByMemberId(@RequestParam memberId: Long, @ModelAttribute pageParam: PageParam): PageResponseDTO<List<ItemListDTO>> {
        val itemList = findItemUseCase.findItemListByMemberId(memberId, PageRequest.of(pageParam.page, pageParam.size))

        return PageResponseDTO.success(itemList.contents.map { ItemListDTO.from(it) }, itemList.page, itemList.totalPage)
    }

    /**
     * 상품의 상제 정보 조회 메서드
     * @param : 상품의 ID
     * @return : 상품의 상세정보
     * */
    @GetMapping("/participant/{itemId}")
    fun getRedis(@PathVariable itemId: String): ResponseDTO<ItemInfoDTO?> {
        return ResponseDTO.success(findItemUseCase.findByItemId(itemId)?.let { ItemInfoDTO.from(it)} )
    }
}