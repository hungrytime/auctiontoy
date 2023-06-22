package com.auctiontoyapi.adapter.`in`.dto

import com.auctiontoyapi.adapter.out.vo.MemberTokenVO

data class MemberTokenDTO(
    val memberId: Long?,
    val token: String
) {
    companion object {
        fun from(memberToken: MemberTokenVO) = MemberTokenDTO(
            memberId = memberToken.memberId,
            token = memberToken.token
        )
    }
}
