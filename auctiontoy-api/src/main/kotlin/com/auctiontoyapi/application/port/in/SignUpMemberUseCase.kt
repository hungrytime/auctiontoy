package com.auctiontoyapi.application.port.`in`

import com.auctiontoyapi.adapter.out.vo.MemberVO

interface SignUpMemberUseCase {
    fun signUpMember(member: MemberVO)
}