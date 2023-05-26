package com.auctiontoyapi.application.port.out

import com.auctiontoyapi.adapter.out.vo.MemberVO

interface JoinMemberPort {
    fun joinMember(member: MemberVO)
}