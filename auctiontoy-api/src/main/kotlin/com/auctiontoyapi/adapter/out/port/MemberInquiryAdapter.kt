package com.auctiontoyapi.adapter.out.port

import com.auctionpersistence.jpa.repositories.MemberJpaRepository
import com.auctiontoyapi.application.port.out.FindMemberPort
import com.auctiontoydomain.Member
import com.auctiontoydomain.MemberStatus
import org.springframework.stereotype.Component

@Component
class MemberInquiryAdapter(
    private val memberJpaRepository: MemberJpaRepository
) : FindMemberPort {
    override fun findMemberByUserId(id: String): Member? {
        return memberJpaRepository.findByUserId(id)?.to()
    }

    override fun findMemberByUserIdAndStatus(id: String, status: String): Member? {
        return memberJpaRepository.findByUserIdAndStatus(id, MemberStatus.valueOf(status))?.to()
    }
}