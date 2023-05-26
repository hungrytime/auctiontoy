package com.example.auctiontoyapi.service

import com.example.auctiontoyapi.adapter.out.vo.MemberVO
import com.example.auctiontoyapi.application.port.`in`.FindMemberUseCase
import com.example.auctiontoyapi.application.port.out.FindMemberPort
import com.example.auctiontoyapi.common.jwt.JwtTokenProvider
import com.example.auctiontoydomain.exception.BusinessException
import com.example.auctiontoydomain.exception.enum.ResultCode
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Service

@Service
class MemberInquiryService(
    private val findMemberPort: FindMemberPort,
    private val authenticationManager: AuthenticationManager,
    private val jwtTokenProvider: JwtTokenProvider
) : FindMemberUseCase{
    override fun findMemberByMemberId(id: String): MemberVO {
        return MemberVO.from(findMemberPort.findMemberById(id))
    }

    override fun signIn(username: String, password: String): String {
        try {
            // 인증시도
            authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(username, password, null)
            )
        } catch (e: Exception) {
            println(e.message)
            throw BusinessException(ResultCode.FAIL, e.message)
        }
        // 예외가 발생하지 않았다면 인증에 성공한 것.
        // 토큰 생성
        val token = jwtTokenProvider.createToken(username)

        return token
    }
}