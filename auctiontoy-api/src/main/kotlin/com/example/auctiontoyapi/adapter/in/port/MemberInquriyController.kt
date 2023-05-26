package com.example.auctiontoyapi.adapter.`in`.port

import com.example.auctiontoyapi.adapter.`in`.common.dto.ResponseDTO
import com.example.auctiontoyapi.adapter.`in`.dto.JoinMemberDTO
import com.example.auctiontoyapi.application.port.`in`.FindMemberUseCase
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/member")
class MemberInquriyController(
    private val findMemberUseCase: FindMemberUseCase
) {

    @GetMapping("/signin")
    fun signIn(@RequestBody member: JoinMemberDTO): ResponseDTO<String> {
        val token = findMemberUseCase.signIn(member.username, member.password)
        println(token)
        return ResponseDTO.success(token)
    }

}