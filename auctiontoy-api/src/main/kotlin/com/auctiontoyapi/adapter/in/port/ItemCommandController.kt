package com.auctiontoyapi.adapter.`in`.port

import com.auctiontoyapi.adapter.`in`.dto.RegisterItemDTO
import com.auctiontoyapi.application.port.`in`.BidItemUseCase
import com.auctiontoyapi.application.port.`in`.ModifyItemUseCase
import com.auctiontoyapi.application.port.`in`.RegisterItemUseCase
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/item")
class ItemCommandController(
    private val itemUseCase: RegisterItemUseCase,
    private val modifyItemUseCase: ModifyItemUseCase,
    private val bidItemUseCase: BidItemUseCase
) {
    @PostMapping("/register")
    fun registerItem(@RequestBody item: RegisterItemDTO): String {
        itemUseCase.register(item.toVO())
        return "OK"
    }

//    @PostMapping("/bid")
//    fun bidItem(@RequestBody item: BidItemDTO): String {
//
//    }

    @PostMapping("/produce")
    fun produce(@RequestParam msg: String): String {
        bidItemUseCase.tryBid(msg)
        return "OK"
    }
}