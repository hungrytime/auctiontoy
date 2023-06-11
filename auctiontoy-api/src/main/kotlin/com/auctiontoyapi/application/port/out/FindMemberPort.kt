package com.auctiontoyapi.application.port.out

import com.auctiontoydomain.Member

interface FindMemberPort {
    fun findMemberByUserId(id: String): Member?
    fun findMemberByUserIdAndStatus(id: String, status: String): Member?
}