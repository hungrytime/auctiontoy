package com.auctiontoyapi.adapter.`in`.port

import com.auctiontoyapi.adapter.`in`.common.dto.ResponseDTO
import com.auctiontoyapi.adapter.`in`.dto.JoinMemberDTO
import com.auctiontoyapi.adapter.`in`.dto.MemberInfoDTO
import com.auctiontoyapi.adapter.`in`.dto.SignMemberDTO
import com.auctiontoyapi.application.port.`in`.FindMemberUseCase
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/member")
class MemberInquriyController(
    private val findMemberUseCase: FindMemberUseCase
) {

    /**
     *  멤버 로그인을 위한 메서드
     *  @param : 로그인을 위한 정보 (id, password)
     *  @return : logind을 위한 token 정보
     * */
    @GetMapping("/sign-in")
    fun signIn(@RequestBody member: SignMemberDTO): ResponseDTO<String> {
        val token = findMemberUseCase.signIn(member.id, member.password)
        return ResponseDTO.success(token)
    }

    @GetMapping("/info")
    fun getMemberInfo(@RequestParam memberId: String): ResponseDTO<MemberInfoDTO> {
        val member = findMemberUseCase.findMemberByMemberId(memberId) ?: throw Exception("$memberId 로 등록된 정보가 존재하지 않습니다")
        return ResponseDTO.success(MemberInfoDTO.from(member))
    }
}