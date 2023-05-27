package com.auctiontoydomain

data class Member(
    val memberId: Long? = null,
    val id: String,
    val password: String,
    val name: String,
    //기본 ACTIVATED로 하고 탈퇴시 REVOKED로 변경
    val memberStatus: MemberStatus = MemberStatus.ACTIVATED,
    val createdDate: String? = null
)

enum class MemberStatus {
    ACTIVATED,
    REVOKED
}

