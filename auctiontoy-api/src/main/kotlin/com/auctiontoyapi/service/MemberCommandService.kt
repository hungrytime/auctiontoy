package com.auctiontoyapi.service

import com.auctiontoyapi.adapter.out.port.MemberCommandAdapter
import com.auctiontoyapi.adapter.out.port.MemberInquiryAdapter
import com.auctiontoyapi.adapter.out.vo.MemberVO
import com.auctiontoyapi.application.port.`in`.RevokeMemberUseCase
import com.auctiontoyapi.application.port.`in`.SignUpMemberUseCase
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class MemberCommandService(
    private val memberInquiryAdapter: MemberInquiryAdapter,
    private val memberCommandAdapter: MemberCommandAdapter,
    private val passwordEncoder: BCryptPasswordEncoder
) : SignUpMemberUseCase, RevokeMemberUseCase {

    override fun signUp(member: MemberVO) {
        memberCommandAdapter.signUp(MemberVO(member.id, passwordEncoder.encode(member.password), member.name))
    }

    //TODO 로그인 시 탈퇴된 회원인지 확인 필요
    override fun rovoke(memberId: String) {
        val member = memberInquiryAdapter.findMemberById(memberId) ?: throw Exception("찾고자하는 멤버가 없습니다 memberId : $memberId")
        member.changeRevoked()
        memberCommandAdapter.save(member)
    }
}