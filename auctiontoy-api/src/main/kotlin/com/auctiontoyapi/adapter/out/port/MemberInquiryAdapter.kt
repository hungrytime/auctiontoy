package com.auctiontoyapi.adapter.out.port

import com.auctionpersistence.jpa.repositories.MemberJpaRepository
import com.auctionpersistence.redis.service.RedisService
import com.auctiontoyapi.application.port.out.FindMemberPort
import com.auctiontoyapi.application.port.out.SignInMemberPort
import com.auctiontoydomain.Member
import com.auctiontoydomain.MemberStatus
import org.springframework.stereotype.Component

@Component
class MemberInquiryAdapter(
    private val memberJpaRepository: MemberJpaRepository,
    private val redisService: RedisService
) : FindMemberPort, SignInMemberPort {
    override fun findByMemberId(memberId: Long): Member? {
        val member = memberJpaRepository.findById(memberId)
        if (member.isPresent) return member.get().to()
        return null
    }

    override fun findMemberByUserId(id: String): Member? {
        return memberJpaRepository.findByUserId(id)?.to()
    }

    override fun findMemberByUserIdAndStatus(id: String, status: String): Member? {
        return memberJpaRepository.findByUserIdAndStatus(id, MemberStatus.valueOf(status))?.to()
    }

    override fun signInMember(token: String) {
        redisService.set(token, "tokenValue")
    }
}