package com.auctiontoyapi.service

import com.auctiontoyapi.adapter.out.port.MemberCommandAdapter
import com.auctiontoyapi.adapter.out.port.MemberInquiryAdapter
import com.auctiontoyapi.adapter.out.vo.MemberVO
import com.auctiontoydomain.Member
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.every
import io.mockk.mockk
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

internal class MemberCommandServiceTest : BehaviorSpec() {
    override fun isolationMode(): IsolationMode = IsolationMode.SingleInstance

    init {
        val memberInquiryAdapter = mockk<MemberInquiryAdapter>()
        val memberCommandAdapter = mockk<MemberCommandAdapter>()
        val bCryptPasswordEncoder = mockk<BCryptPasswordEncoder>()

        val memberCommandService = MemberCommandService(
            memberInquiryAdapter, memberCommandAdapter, bCryptPasswordEncoder
        )

        Given("멤버 정보") {
            val defaultMember = Member(
                id = "user",
                password = "pass",
                name = "kim"
            )
            When("탈퇴 하려고 할 때") {
                Then("해당 멤버가 탈퇴 가능한 상태가 아닐 때") {
                    every { memberInquiryAdapter.findMemberByUserIdAndStatus(any(), any()) } returns null
                    shouldThrow<Exception> {
                        memberCommandService.revoke("user")
                    }
                }

                Then("멤버가 탈퇴 가능한 상태일 때") {
                    every { memberInquiryAdapter.findMemberByUserIdAndStatus(any(), any()) } returns defaultMember
                    every { memberCommandAdapter.save(defaultMember) } returns mockk()
                    memberCommandService.revoke("user")
                }
            }

            When("가입") {
                val memberVO = MemberVO.from(defaultMember)
                every { bCryptPasswordEncoder.encode(any()) } returns "pass"
                every { memberCommandAdapter.signUp(memberVO) } returns mockk()
                memberCommandService.signUp(memberVO)
            }

            When("로그 아웃") {
                val token = "Bearer token"
                every { memberCommandService.logout(token) } returns mockk()
            }
        }
    }
}