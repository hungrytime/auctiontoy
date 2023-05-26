package com.example.auctiontoyapi.application.port.`in`

import com.example.auctiontoyapi.adapter.out.vo.MemberVO

interface FindMemberUseCase {
    fun findMemberByMemberId(id: String): MemberVO
    fun signIn(username: String, password: String): String
}