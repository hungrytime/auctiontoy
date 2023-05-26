package com.auctiontoyapi.adapter.out.port

import com.auctionpersistence.jpa.entity.MemberJpaEntity
import com.auctionpersistence.jpa.repositories.MemberJpaRepository
import com.auctiontoyapi.adapter.out.vo.MemberVO
import com.auctiontoyapi.application.port.out.JoinMemberPort
import org.springframework.stereotype.Component

@Component
class MemberCommandAdapter(
    private val memberJpaRepositories: MemberJpaRepository
) : JoinMemberPort{
    @Override
    override fun joinMember(member: MemberVO) {
        memberJpaRepositories.save(MemberJpaEntity.from(member.to()))
    }
}