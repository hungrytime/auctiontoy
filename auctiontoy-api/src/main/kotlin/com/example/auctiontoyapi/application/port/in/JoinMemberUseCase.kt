package com.example.auctiontoyapi.application.port.`in`

import com.example.auctiontoyapi.adapter.out.vo.MemberVO

interface JoinMemberUseCase {
    fun joinMember(member: MemberVO)
}