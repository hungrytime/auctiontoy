package com.auctiontoyapi.service

import com.auctiontoyapi.adapter.out.port.MemberCommandAdapter
import com.auctiontoyapi.adapter.out.vo.MemberVO
import com.auctiontoyapi.application.port.`in`.JoinMemberUseCase
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class MemberCommandService(
    private val memberCommandAdapter: MemberCommandAdapter,
    private val passwordEncoder: BCryptPasswordEncoder
) : JoinMemberUseCase {
    @Override
    override fun joinMember(member: MemberVO) {

        memberCommandAdapter.joinMember(MemberVO(member.id, passwordEncoder.encode(member.password), member.name))
    }
}