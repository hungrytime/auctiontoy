package com.auctiontoyapi.adapter.`in`.port

import com.auctiontoyapi.adapter.`in`.common.dto.ResponseDTO
import com.auctiontoyapi.adapter.`in`.dto.JoinMemberDTO
import com.auctiontoyapi.application.port.`in`.FindMemberUseCase
import com.auctiontoyapi.application.port.`in`.LogoutUseCase
import com.auctiontoyapi.application.port.`in`.RevokeMemberUseCase
import com.auctiontoyapi.application.port.`in`.SignUpMemberUseCase
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest


@RestController
@RequestMapping("/member")
class MemberCommandController(
    private val signUpMemberUseCase: SignUpMemberUseCase,
    private val findMemberUseCase: FindMemberUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val revokeMemberUseCase: RevokeMemberUseCase
) {
    /**
     * 멤버 가입을 하기 위해 사용하는 API
     * @param : 멤버 가입 정보
     * @return : 성공을 알리는 메세지
     * */
    @PostMapping("/sign-up")
    fun join(@RequestBody member: JoinMemberDTO): ResponseDTO<String> {
        val checkExistMember = findMemberUseCase.findMemberByMemberId(member.id)
        if(checkExistMember != null) throw Exception("이미 같은 ID가 존재 합니다. ${member.id}")
        signUpMemberUseCase.signUp(member.toVO())
        return ResponseDTO.success("OK")
    }

    /**
     * 로그아웃을 위한 메서드(토큰 정보를 제거해 로그아웃 시킨다)
     * @param : httpServeletRequest
     * @return : 로그아웃 성공을 알리는 response
     * */
    @PostMapping( "/logout")
    fun messageForHeader(request: HttpServletRequest): ResponseDTO<String> {
        val header = request.getHeader("authorization") ?: throw Exception("토큰이 존재하지 않습니다")
        logoutUseCase.logout(header)
        return ResponseDTO.success("OK")
    }

    @PostMapping("/revoke")
    fun revoke(@RequestParam memberUserId: String): ResponseDTO<String> {
        revokeMemberUseCase.revoke(memberUserId)
        return ResponseDTO.success("OK")
    }
}