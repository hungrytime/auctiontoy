package com.auctiontoyapi.adapter.`in`.port

import com.auctiontoyapi.adapter.`in`.dto.RegisterItemDTO
import com.auctiontoyapi.application.port.`in`.RegisterItemUseCase
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/item")
class ItemCommandController(
    private val itemUseCase: RegisterItemUseCase
) {
    @PostMapping("/register")
    fun registerItem(@RequestBody item: RegisterItemDTO): String {
        itemUseCase.register(item.to())
        return "OK"
    }
}