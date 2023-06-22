package com.auctiontoyapi.application.port.`in`

import com.auctiontoyapi.adapter.out.vo.MemberTokenVO

interface SignInMemberUseCase {
    fun signIn(id: String, password: String): MemberTokenVO
}