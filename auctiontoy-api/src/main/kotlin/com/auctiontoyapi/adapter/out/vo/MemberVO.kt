package com.auctiontoyapi.adapter.out.vo

import com.auctiontoydomain.Member

data class MemberVO (
    val memberId: Long? = null,
    val id: String,
    val password: String,
    val name: String,
    val createdDate: String? = null
) {
    fun to() = Member(
        id = id,
        password = password,
        name = name
    )

    companion object {
        fun from(member: Member) = MemberVO(
            memberId = member.memberId,
            id = member.id,
            password = member.password,
            name = member.name,
            createdDate = member.createdDate
        )
    }
}