package com.auctiontoyapi.application.port.out

import com.auctiontoydomain.Member

interface SaveMemberPort {
    fun save(member: Member)
}