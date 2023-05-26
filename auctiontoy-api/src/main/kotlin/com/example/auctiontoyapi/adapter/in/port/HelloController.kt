package com.example.auctiontoyapi.adapter.`in`.port

import com.example.auctiontoyapi.adapter.`in`.common.dto.ResponseDTO
import mu.KLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping
class HelloController(
    @Value("\${jwt.secret}")
    private val secretKey: String = ""
) {

    @GetMapping("/hello")
    fun hello(): ResponseDTO<String> {
        return ResponseDTO.success("hello/hello")
    }

    companion object: KLogging()
}