package com.auctiontoyapi.service

import com.auctiontoyapi.adapter.out.port.MemberCommandAdapter
import com.auctiontoyapi.adapter.out.port.MemberInquiryAdapter
import com.auctiontoyapi.adapter.out.vo.MemberVO
import com.auctiontoyapi.application.port.`in`.LogoutUseCase
import com.auctiontoyapi.application.port.`in`.RevokeMemberUseCase
import com.auctiontoyapi.application.port.`in`.SignUpMemberUseCase
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class MemberCommandService(
    private val memberInquiryAdapter: MemberInquiryAdapter,
    private val memberCommandAdapter: MemberCommandAdapter,
    private val passwordEncoder: BCryptPasswordEncoder
) : SignUpMemberUseCase, RevokeMemberUseCase, LogoutUseCase {

    override fun signUp(member: MemberVO) {
        memberCommandAdapter.signUp(
            MemberVO(
                id = member.id,
                password = passwordEncoder.encode(member.password),
                name = member.name
            )
        )
    }

    override fun revoke(id: String) {
        val member = memberInquiryAdapter.findMemberByUserIdAndStatus(id, ACTIVE_MEMBER)
            ?: throw Exception("찾고자하는 멤버가 없습니다 memberId : $id")
        member.changeRevoked()
        memberCommandAdapter.save(member)
    }

    override fun logout(header: String) {
        memberCommandAdapter.logout(extractBearerTokenFromHeader(header))
    }

    // 헤더의 정보에서 bearer 을 제거한 토큰 정보를 찾아내는 메서드
    private fun extractBearerTokenFromHeader(header: String): String {
        return header.substring("Bearer ".length)
    }

    companion object {
        const val ACTIVE_MEMBER = "ACTIVATED"
    }
}