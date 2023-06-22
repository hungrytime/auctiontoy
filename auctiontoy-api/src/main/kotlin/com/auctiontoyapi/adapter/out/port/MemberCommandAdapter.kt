package com.auctiontoyapi.adapter.out.port

import com.auctionpersistence.jpa.entity.MemberJpaEntity
import com.auctionpersistence.jpa.repositories.MemberJpaRepository
import com.auctionpersistence.redis.service.RedisService
import com.auctiontoyapi.adapter.out.vo.MemberVO
import com.auctiontoyapi.application.port.out.LogoutPort
import com.auctiontoyapi.application.port.out.SaveMemberPort
import com.auctiontoyapi.application.port.out.SignUpMemberPort
import com.auctiontoydomain.Member
import mu.KLogging
import org.hibernate.exception.ConstraintViolationException
import org.springframework.stereotype.Component
import java.sql.SQLIntegrityConstraintViolationException

@Component
class MemberCommandAdapter(
    private val memberJpaRepositories: MemberJpaRepository,
    private val redisService: RedisService
) : SignUpMemberPort, SaveMemberPort, LogoutPort {

    override fun signUp(member: MemberVO) {
        try {
            memberJpaRepositories.save(MemberJpaEntity.from(member.to()))
        } catch (e: Exception) {
            logger.info(e.message)
            throw Exception(e.message)
        }
    }

    override fun save(member: Member) {
        memberJpaRepositories.save(MemberJpaEntity.from(member))
    }

    companion object: KLogging()

    override fun logout(token: String) {
        redisService.delete(token)
    }


}