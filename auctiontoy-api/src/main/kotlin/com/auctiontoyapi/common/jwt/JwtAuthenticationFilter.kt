package com.auctiontoyapi.common.jwt

import com.auctionpersistence.redis.service.RedisService
import mu.KLogging
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtAuthenticationFilter(
    private val jwtTokenProvider: JwtTokenProvider,
    private val redisService: RedisService
): OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        // 헤더에 Authorization이 있다면 가져온다.
        val authorizationHeader: String = request.getHeader("Authorization") ?: return filterChain.doFilter(request, response)
        // Bearer타입 토큰이 있을 때 가져온다.
        val token = authorizationHeader.substring("Bearer ".length)

        // 레디스에 토큰 정보가 있는지 확인 후 토큰 검증
        if (redisService.get(token) != null && jwtTokenProvider.validateToken(token)) {
            // 검증 성공 로그
            logger.info("토큰 검증 성공")
            val username = jwtTokenProvider.parseUsername(token)
            // username으로 AuthenticationToken 생성
            val authentication: Authentication = jwtTokenProvider.getAuthentication(username)
            // SecurityContext에 등록
            SecurityContextHolder.getContext().authentication = authentication
        } else {
            // 검증 실패 로그
            logger.info("로그인 실패")
        }

        filterChain.doFilter(request, response)
    }

    companion object: KLogging()
}