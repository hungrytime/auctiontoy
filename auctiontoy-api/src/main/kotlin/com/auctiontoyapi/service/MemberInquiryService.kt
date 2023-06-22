package com.auctiontoyapi.service

import com.auctiontoyapi.adapter.out.vo.MemberVO
import com.auctiontoyapi.application.port.`in`.FindMemberUseCase
import com.auctiontoyapi.application.port.out.FindMemberPort
import com.auctiontoyapi.application.port.out.SignInMemberPort
import com.auctiontoyapi.common.jwt.JwtTokenProvider
import com.auctiontoydomain.exception.BusinessException
import com.auctiontoydomain.exception.MemberException
import com.auctiontoydomain.exception.enum.ResultCode
import mu.KLogging
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Service

@Service
class MemberInquiryService(
    private val findMemberPort: FindMemberPort,
    private val signInMemberPort: SignInMemberPort,
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
            // 인증 실패
            logger.error(e.message)
            throw MemberException(ResultCode.MEMBER_AUTHENTICATION_FAIL, e.message)
        }
        // 토큰 생성
        val token = jwtTokenProvider.createToken(id)
        // Redis에 토큰 정보 저장
        signInMemberPort.signInMember(token)
        return token
    }

    companion object : KLogging()
}