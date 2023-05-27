package com.auctiontoyapi.service

import com.auctiontoyapi.adapter.out.port.MemberInquiryAdapter
import com.auctiontoyapi.common.jwt.UserDetailsImpl
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl(
    private val memberInquiryAdapter: MemberInquiryAdapter
) : UserDetailsService {
    override fun loadUserByUsername(username: String?): UserDetails {
        val member = memberInquiryAdapter.findMemberById(username!!) ?: throw Exception("ID : $username 멤버가 존재하지 않습니다")
        return UserDetailsImpl(member)
    }
}