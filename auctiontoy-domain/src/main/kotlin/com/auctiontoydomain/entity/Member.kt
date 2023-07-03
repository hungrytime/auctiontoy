package com.auctiontoydomain

data class Member(
    val memberId: Long? = null,
    val id: String,
    val password: String,
    val name: String,
    //기본 ACTIVATED로 하고 탈퇴시 REVOKED로 변경
    var memberStatus: MemberStatus = MemberStatus.ACTIVATED,
    val createdDate: String? = null
) {
    fun changeRevoked() {
        this.memberStatus = MemberStatus.REVOKED
    }
}

enum class MemberStatus {
    ACTIVATED,
    REVOKED
}

