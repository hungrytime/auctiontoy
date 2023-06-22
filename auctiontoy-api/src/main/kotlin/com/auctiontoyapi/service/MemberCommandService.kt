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
        memberCommandAdapter.signUp(MemberVO(member.id, passwordEncoder.encode(member.password), member.name))
    }

    override fun rovoke(id: String) {
        val member = memberInquiryAdapter.findMemberByUserIdAndStatus(id, REVOKED_MEMBER) ?: throw Exception("찾고자하는 멤버가 없습니다 memberId : $id")
        member.changeRevoked()
        memberCommandAdapter.save(member)
    }

    override fun logout(header: String) {
        memberCommandAdapter.logout(extractTokenFromHeader(header))
    }

    private fun extractTokenFromHeader(header: String): String {
        return header.substring("Bearer ".length)
    }

    companion object {
        const val REVOKED_MEMBER = "REVOKED"
    }
}