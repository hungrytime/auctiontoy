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
        return UserDetailsImpl(memberInquiryAdapter.findMemberById(username!!))
    }
}