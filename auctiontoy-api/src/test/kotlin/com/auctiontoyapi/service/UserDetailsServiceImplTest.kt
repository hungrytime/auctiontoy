package com.auctiontoyapi.service

import com.auctiontoyapi.adapter.out.port.MemberInquiryAdapter
import com.auctiontoydomain.Member
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.every
import io.mockk.mockk

internal class UserDetailsServiceImplTest: BehaviorSpec() {
    override fun isolationMode(): IsolationMode = IsolationMode.SingleInstance

    init {
        val memberInquiryAdapter = mockk<MemberInquiryAdapter>()

        val userDetailsServiceImpl = UserDetailsServiceImpl(memberInquiryAdapter)

        Given("User 정보") {
            When("User 정보가 없을 때") {
                every { memberInquiryAdapter.findMemberByUserId("user") } returns null

                Then("에러 발생") {
                    shouldThrow<Exception> {
                        userDetailsServiceImpl.loadUserByUsername("user")
                    }
                }
            }

            When("User 정보가 있을 때") {
                val member = Member(
                    id = "user",
                    password = "pass",
                    name = "kim"
                )

                every { memberInquiryAdapter.findMemberByUserId("user") } returns member
                every { userDetailsServiceImpl.loadUserByUsername("user") } returns mockk()
            }
        }
    }
}