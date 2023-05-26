package com.auctiontoyapi.application.port.`in`

import com.auctiontoyapi.adapter.out.vo.MemberVO

interface FindMemberUseCase {
    fun findMemberByMemberId(id: String): MemberVO
    fun signIn(username: String, password: String): String
}