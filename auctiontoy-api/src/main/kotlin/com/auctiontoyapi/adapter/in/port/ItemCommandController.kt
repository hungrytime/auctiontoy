package com.auctiontoyapi.adapter.`in`.port

import com.auctiontoyapi.adapter.`in`.dto.RegisterItemDTO
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/item")
class ItemCommandController {
    @PostMapping("/register")
    fun registerItem(@RequestBody item: RegisterItemDTO): String {
        return "OK"
    }
}