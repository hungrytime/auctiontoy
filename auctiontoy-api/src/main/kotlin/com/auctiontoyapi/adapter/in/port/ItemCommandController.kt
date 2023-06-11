package com.auctiontoyapi.adapter.`in`.port

import com.auctiontoyapi.adapter.`in`.common.dto.ResponseDTO
import com.auctiontoyapi.adapter.`in`.dto.BidItemDTO
import com.auctiontoyapi.adapter.`in`.dto.ModifyItemDTO
import com.auctiontoyapi.adapter.`in`.dto.RegisterItemDTO
import com.auctiontoyapi.application.port.`in`.BidItemUseCase
import com.auctiontoyapi.application.port.`in`.ModifyItemUseCase
import com.auctiontoyapi.application.port.`in`.RegisterItemUseCase
import org.springframework.web.bind.annotation.*

/**
 * 클라이언트로 부터 호출을 받게 되는 컨트롤러이며 주로 등록이나 입찰등 데이터베이스에 update, write를 담당하고 있다.
 * */
@RestController
@RequestMapping("/item")
class ItemCommandController(
    private val itemUseCase: RegisterItemUseCase,
    private val modifyItemUseCase: ModifyItemUseCase,
    private val bidItemUseCase: BidItemUseCase
) {
    /**
     * 제품을 등록하는 함수
     * @param : Item을 등록하기 위한 아이템 정보
     * @return : 성공 메세지
     * */
    @PostMapping("/register")
    fun registerItem(@RequestBody item: RegisterItemDTO): ResponseDTO<String> {
        itemUseCase.register(item.toVO())
        return ResponseDTO.success("OK")
    }

    @PostMapping("/produce")
    fun produce(@RequestParam msg: String): String {
        bidItemUseCase.tryBid(msg)
        return "OK"
    }

    /**
     * 입찰을 위한 함수
     * @param : Item을 입찰하기 위한 정보
     * @return : 성공 메세지
     * */
    @PostMapping("/bid")
    fun bid(@RequestBody item: BidItemDTO): ResponseDTO<Unit> {
        bidItemUseCase.bid(item.toVO())
        return ResponseDTO.success()
    }

    /**
     * 입찰전 정보 수정을 위한 함수
     * @param : Item을 수정하기 위한 정보
     * @return : 성공 메세지
     * */
    @PostMapping("/modify")
    fun modify(@RequestBody item: ModifyItemDTO): ResponseDTO<Unit> {
        modifyItemUseCase.modify(item.toVO())
        return ResponseDTO.success()
    }
}