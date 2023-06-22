package com.auctiontoyapi.adapter.out.vo

data class MemberTokenVO(
    // 멤버 테이블에 있는 MemberId
    val memberId: Long?,
    // 발급된 토큰 정보
    val token: String
)
