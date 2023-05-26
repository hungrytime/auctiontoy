package com.example.auctiontoyapi.adapter.out.vo

import com.example.auctiontoydomain.Member

data class MemberVO (
    val id: String,
    val password: String,
    val name: String
) {
    fun to() = Member(
        id = id,
        password = password,
        name = name
    )

    companion object {
        fun from(member: Member) = MemberVO(
            id = member.id,
            password = member.password,
            name = member.name
        )
    }
}