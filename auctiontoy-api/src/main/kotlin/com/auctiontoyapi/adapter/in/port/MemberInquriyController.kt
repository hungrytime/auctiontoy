package com.auctiontoyapi.adapter.`in`.port

import com.auctiontoyapi.adapter.`in`.common.dto.ResponseDTO
import com.auctiontoyapi.adapter.`in`.dto.MemberInfoDTO
import com.auctiontoyapi.adapter.`in`.dto.SignInMemberDTO
import com.auctiontoyapi.adapter.`in`.dto.MemberTokenDTO
import com.auctiontoyapi.application.port.`in`.FindMemberUseCase
import com.auctiontoyapi.application.port.`in`.SignInMemberUseCase
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/member")
class MemberInquriyController(
    private val findMemberUseCase: FindMemberUseCase,
    private val signInMemberUseCase: SignInMemberUseCase
) {

    /**
     *  멤버 로그인을 위한 메서드
     *  @param : 로그인을 위한 정보 (id, password)
     *  @return : logind을 위한 token 정보
     * */
    @PostMapping("/sign-in")
    fun signIn(@RequestBody member: SignInMemberDTO): ResponseDTO<MemberTokenDTO> {
        val memberInfo = signInMemberUseCase.signIn(member.id, member.password)
        return ResponseDTO.success(MemberTokenDTO.from(memberInfo))
    }

    /**
     * memberId 로 멤버 정보 가져오는 메서드
     * @param : memberId
     * @return : member 정보(이름, 만든 날짜)
     * */
    @GetMapping("/info")
    fun getMemberInfo(@RequestParam memberId: String): ResponseDTO<MemberInfoDTO> {
        val member = findMemberUseCase.findMemberByMemberId(memberId) ?: throw Exception("$memberId 로 등록된 정보가 존재하지 않습니다")
        return ResponseDTO.success(MemberInfoDTO.from(member))
    }
}