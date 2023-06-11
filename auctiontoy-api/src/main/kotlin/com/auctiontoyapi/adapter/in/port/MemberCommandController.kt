package com.auctiontoyapi.adapter.`in`.port

import com.auctiontoyapi.adapter.`in`.common.dto.ResponseDTO
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
    /**
     * param : 멤버 가입 정보
     * return : 성공을 알리는 메세지
     * 멤버 가입을 하기 위해 사용하는 API
     * */
    @PostMapping("/sign-up")
    fun join(@RequestBody member: JoinMemberDTO): ResponseDTO<String> {
        val checkExistMember = findMemberUseCase.findMemberByMemberId(member.id)
        if(checkExistMember != null) throw Exception("이미 같은 ID가 존재 합니다. ${member.id}")
        signUpMemberUseCase.signUp(member.toVO())
        return ResponseDTO.success("OK")
    }
}