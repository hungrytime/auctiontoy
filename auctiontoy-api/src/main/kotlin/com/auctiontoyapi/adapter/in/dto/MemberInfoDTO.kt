package com.auctiontoyapi.adapter.`in`.dto

import com.auctiontoyapi.adapter.out.vo.MemberVO

data class MemberInfoDTO(
    val memberName: String,
    val createdDate: String
) {
    companion object{
        fun from(member: MemberVO) = MemberInfoDTO(
            memberName = member.name,
            createdDate = member.createdDate!!
        )
    }
}