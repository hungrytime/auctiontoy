package com.auctiontoyapi.application.port.out

import com.auctiontoydomain.Member

interface FindMemberPort {
    fun findMemberById(id: String): Member
}