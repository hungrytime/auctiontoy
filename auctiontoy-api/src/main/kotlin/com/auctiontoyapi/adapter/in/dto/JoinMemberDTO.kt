package com.auctiontoyapi.adapter.`in`.dto

import com.auctiontoyapi.adapter.out.vo.MemberVO

data class JoinMemberDTO(
    val id: String,
    val password: String,
    val name: String
) {
    fun to() = MemberVO(
        id = id,
        password = password,
        name = name
    )
}