package com.auctiontoyapi.adapter.`in`.port

import com.auctiontoyapi.adapter.`in`.dto.JoinMemberDTO
import com.auctiontoyapi.application.port.`in`.JoinMemberUseCase
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/member")
class MemberCommandController(
    private val joinMemberUseCase: JoinMemberUseCase
) {

    @PostMapping("/join")
    fun join(@RequestBody member: JoinMemberDTO): String {
        joinMemberUseCase.joinMember(member.to())
        return "OK"
    }
}