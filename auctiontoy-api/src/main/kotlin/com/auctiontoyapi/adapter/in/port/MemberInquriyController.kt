package com.auctiontoyapi.adapter.`in`.port

import com.auctiontoyapi.adapter.`in`.common.dto.ResponseDTO
import com.auctiontoyapi.adapter.`in`.dto.JoinMemberDTO
import com.auctiontoyapi.adapter.`in`.dto.MemberInfoDTO
import com.auctiontoyapi.application.port.`in`.FindMemberUseCase
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/member")
class MemberInquriyController(
    private val findMemberUseCase: FindMemberUseCase
) {
    @GetMapping("/signin")
    fun signIn(@RequestBody member: JoinMemberDTO): ResponseDTO<String> {
        val token = findMemberUseCase.signIn(member.username, member.password)
        println(token)
        return ResponseDTO.success(token)
    }

    @GetMapping("/info")
    fun getMemberInfo(@RequestParam memberId: String): ResponseDTO<MemberInfoDTO> {
        return ResponseDTO.success(MemberInfoDTO.from(findMemberUseCase.findMemberByMemberId(memberId)))
    }
}