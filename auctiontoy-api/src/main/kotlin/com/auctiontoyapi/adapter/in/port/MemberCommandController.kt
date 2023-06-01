package com.auctiontoyapi.adapter.`in`.port

import com.auctiontoyapi.adapter.`in`.dto.JoinMemberDTO
import com.auctiontoyapi.application.port.`in`.FindMemberUseCase
import com.auctiontoyapi.application.port.`in`.SignUpMemberUseCase
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/member")
class MemberCommandController(
    private val signUpMemberUseCase: SignUpMemberUseCase,
    private val findMemberUseCase: FindMemberUseCase
) {
    @PostMapping("/signup")
    fun join(@RequestBody member: JoinMemberDTO): String {
        val checkExistMember = findMemberUseCase.findMemberByMemberId(member.id)
        if(checkExistMember != null) throw Exception("이미 같은 ID가 존재 합니다. ${member.id}")
        signUpMemberUseCase.signUp(member.to())
        return "OK"
    }
}