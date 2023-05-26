package com.auctiontoyapi.adapter.`in`.dto

import com.auctiontoyapi.adapter.out.vo.MemberVO

data class JoinMemberDTO(
    val username: String,
    val password: String,
    val name: String
) {
    fun to() = MemberVO(
        id = username,
        password = password,
        name = name
    )
}