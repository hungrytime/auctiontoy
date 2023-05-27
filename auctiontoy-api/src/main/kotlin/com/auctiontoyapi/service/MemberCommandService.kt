package com.auctiontoyapi.service

import com.auctiontoyapi.adapter.out.port.MemberCommandAdapter
import com.auctiontoyapi.adapter.out.vo.MemberVO
import com.auctiontoyapi.application.port.`in`.SignUpMemberUseCase
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class MemberCommandService(
    private val memberCommandAdapter: MemberCommandAdapter,
    private val passwordEncoder: BCryptPasswordEncoder
) : SignUpMemberUseCase {
    @Override
    override fun signUpMember(member: MemberVO) {
        memberCommandAdapter.signUpMember(MemberVO(member.id, passwordEncoder.encode(member.password), member.name))
    }
}