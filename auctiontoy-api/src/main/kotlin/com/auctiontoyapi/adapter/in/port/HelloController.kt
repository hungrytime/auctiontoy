package com.auctiontoyapi.adapter.`in`.port

import com.auctiontoyapi.adapter.`in`.common.dto.ResponseDTO
import com.auctiontoyapi.adapter.`in`.dto.SignInMemberDTO
import mu.KLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping
class HelloController(
    @Value("\${jwt.secret}")
    private val secretKey: String = ""
) {

    @GetMapping("/hello")
    fun hello(@RequestParam memberId: String): ResponseDTO<String> {
        logger.info(memberId)
        return ResponseDTO.success("hello/hello")
    }
//
//    @PostMapping("/hello/buddy")
//    fun hello2(@RequestBody memberId: SignInMemberDTO): ResponseDTO<String> {
//        logger.info(memberId.toString())
//        return ResponseDTO.success("hello/hello2")
//    }

    companion object: KLogging()
}