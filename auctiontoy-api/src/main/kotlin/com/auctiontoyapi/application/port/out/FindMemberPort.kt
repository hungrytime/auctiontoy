package com.auctiontoyapi.application.port.out

import com.auctiontoydomain.Member

interface FindMemberPort {
    fun findByMemberId(memberId: Long): Member?
    fun findMemberByUserId(id: String): Member?
    fun findMemberByUserIdAndStatus(id: String, status: String): Member?
}