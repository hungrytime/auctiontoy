package com.example.auctiontoyapi.application.port.out

import com.example.auctiontoyapi.adapter.out.vo.MemberVO

interface JoinMemberPort {
    fun joinMember(member: MemberVO)
}