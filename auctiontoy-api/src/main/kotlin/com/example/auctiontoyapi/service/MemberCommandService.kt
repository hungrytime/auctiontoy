package com.example.auctiontoyapi.service

import com.example.auctiontoyapi.adapter.out.port.MemberCommandAdapter
import com.example.auctiontoyapi.adapter.out.vo.MemberVO
import com.example.auctiontoyapi.application.port.`in`.JoinMemberUseCase
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