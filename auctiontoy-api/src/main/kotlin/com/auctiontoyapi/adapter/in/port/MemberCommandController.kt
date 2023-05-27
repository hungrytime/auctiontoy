package com.auctiontoyapi.adapter.`in`.port

import com.auctiontoyapi.adapter.`in`.dto.JoinMemberDTO
import com.auctiontoyapi.application.port.`in`.SignUpMemberUseCase
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/member")
class MemberCommandController(
    private val signUpMemberUseCase: SignUpMemberUseCase
) {
    @PostMapping("/signup")
    fun join(@RequestBody member: JoinMemberDTO): String {
        signUpMemberUseCase.signUpMember(member.to())
        return "OK"
    }
}