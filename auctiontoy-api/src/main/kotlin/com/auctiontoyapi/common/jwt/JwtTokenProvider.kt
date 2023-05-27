package com.auctiontoyapi.common.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.security.Key
import java.util.*

@Component
class JwtTokenProvider(
    private val userDetailsService: UserDetailsService
) {
    @Value("\${jwt.secret}")
    private val secretKey: String = ""
    private val TOKEN_EXPIRE_TIME: Long = 10 * 60 * 1000L

    private fun generateKey(): Key {
        return Keys.hmacShaKeyFor(secretKey.toByteArray(StandardCharsets.UTF_8))
    }

    // 토큰생성
    fun createToken(username: String): String {
        val claims: Claims = Jwts.claims()
        claims["username"] = username

        return Jwts.builder()
            .setClaims(claims)
            .setExpiration(Date(System.currentTimeMillis() + TOKEN_EXPIRE_TIME))
            .signWith(generateKey(), SignatureAlgorithm.HS256)
            .compact()
    }

    // 토큰에서 username 파싱
    fun parseUsername(token: String): String {
        val claims: Claims = getClaimByToken(token)
        return claims["username"] as String
    }

    // 토큰에서 claim 부분 가져옴
    fun getClaimByToken(token: String) : Claims {
        return Jwts.parserBuilder()
            .setSigningKey(generateKey())
            .build()
            .parseClaimsJws(token)
            .body
    }

    // 토큰의 유효성 + 만료일자 확인
    fun validateToken(jwtToken: String): Boolean {
        return try {
            val claims = Jwts.parserBuilder()
                .setSigningKey(generateKey())
                .build()
                .parseClaimsJws(jwtToken)
            !claims.body.expiration.before(Date())
        } catch (e: Exception) {
            false
        }
    }

    // JWT 토큰에서 인증 정보 조회
    fun getAuthentication(token: String): Authentication {
        val userDetails = userDetailsService.loadUserByUsername(token)
        return UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
    }
}