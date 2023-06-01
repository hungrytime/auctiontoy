package com.auctiontoyapi.application.port.out

import com.auctiontoyapi.adapter.out.vo.MemberVO

interface SignUpMemberPort {
    fun signUp(member: MemberVO)
}