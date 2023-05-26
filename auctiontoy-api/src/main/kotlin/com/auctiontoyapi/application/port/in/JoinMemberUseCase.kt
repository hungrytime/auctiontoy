package com.auctiontoyapi.application.port.`in`

import com.auctiontoyapi.adapter.out.vo.MemberVO

interface JoinMemberUseCase {
    fun joinMember(member: MemberVO)
}