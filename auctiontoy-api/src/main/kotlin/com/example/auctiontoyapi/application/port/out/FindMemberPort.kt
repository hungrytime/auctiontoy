package com.example.auctiontoyapi.application.port.out

import com.example.auctiontoydomain.Member

interface FindMemberPort {
    fun findMemberById(id: String): Member
}