package com.example.auctiontoyapi.adapter.out.port

import com.example.auctionpersistence.jpa.repositories.MemberJpaRepository
import com.example.auctiontoyapi.application.port.out.FindMemberPort
import com.example.auctiontoydomain.Member
import org.springframework.stereotype.Component

@Component
class MemberInquiryAdapter(
    private val memberJpaRepository: MemberJpaRepository
) : FindMemberPort {
    override fun findMemberById(id: String): Member {
        return memberJpaRepository.findByUsername(id)?.to() ?: throw Exception("멤버 없다")
    }
}