package com.example.auctiontoyapi.adapter.out.port

import com.example.auctionpersistence.jpa.entity.MemberJpaEntity
import com.example.auctionpersistence.jpa.repositories.MemberJpaRepository
import com.example.auctiontoyapi.adapter.out.vo.MemberVO
import com.example.auctiontoyapi.application.port.out.JoinMemberPort
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