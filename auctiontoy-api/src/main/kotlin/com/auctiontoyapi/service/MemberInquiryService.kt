package com.auctiontoyapi.service

import com.auctiontoyapi.adapter.out.vo.MemberTokenVO
import com.auctiontoyapi.adapter.out.vo.MemberVO
import com.auctiontoyapi.application.port.`in`.FindMemberUseCase
import com.auctiontoyapi.application.port.`in`.SignInMemberUseCase
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
) : FindMemberUseCase, SignInMemberUseCase {

    // 사용자가 입력하는 userId를 기반으로 멤버 검색
    override fun findMemberByMemberId(id: String): MemberVO? {
        val member = findMemberPort.findMemberByUserId(id) ?: return null
        return MemberVO.from(member)
    }

    // 로그인을 시도하는 메서드. 성공시 토큰과 멤버 id를 찾아 return 한다
    override fun signIn(id: String, password: String): MemberTokenVO {
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
        val member = findMemberPort.findMemberByUserId(id) ?: throw MemberException(
            ResultCode.MEMBER_NOT_FOUND,
            "멤버 정보가 없습니다. $id"
        )
        // 토큰 생성
        val token = jwtTokenProvider.createToken(id)
        // Redis에 토큰 정보 저장
        signInMemberPort.signInMember(token)
        return MemberTokenVO(member.memberId, token)
    }

    companion object : KLogging()
}