package com.auctiontoyapi.service

import com.auctiontoyapi.adapter.out.vo.MemberVO
import com.auctiontoyapi.application.port.`in`.FindMemberUseCase
import com.auctiontoyapi.application.port.out.FindMemberPort
import com.auctiontoyapi.common.jwt.JwtTokenProvider
import com.auctiontoydomain.exception.BusinessException
import com.auctiontoydomain.exception.enum.ResultCode
import mu.KLogging
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Service

@Service
class MemberInquiryService(
    private val findMemberPort: FindMemberPort,
    private val authenticationManager: AuthenticationManager,
    private val jwtTokenProvider: JwtTokenProvider
) : FindMemberUseCase {
    override fun findMemberByMemberId(id: String): MemberVO? {
        val member = findMemberPort.findMemberByUserId(id) ?: return null
        return MemberVO.from(member)
    }

    override fun signIn(id: String, password: String): String {
        try {
            // 인증시도
            authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(id, password, null)
            )
        } catch (e: Exception) {
            logger.error(e.message)
            throw BusinessException(ResultCode.FAIL, e.message)
        }
        // 토큰 생성
        return jwtTokenProvider.createToken(id)
    }

    companion object : KLogging()
}