package com.auctiontoyapi.service

import com.auctiontoyapi.application.port.out.FindMemberPort
import com.auctiontoyapi.application.port.out.SignInMemberPort
import com.auctiontoyapi.common.jwt.JwtTokenProvider
import com.auctiontoydomain.Member
import com.auctiontoydomain.exception.MemberException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.springframework.security.authentication.AuthenticationManager

internal class MemberInquiryServiceTest: BehaviorSpec() {
    override fun isolationMode(): IsolationMode = IsolationMode.SingleInstance
    init {

        val findMemberPort = mockk<FindMemberPort>()
        val signInMemberPort = mockk<SignInMemberPort>()
        val authenticationManage = mockk<AuthenticationManager>()
        val jwtTokenProvider = mockk<JwtTokenProvider>()

        val memberInquiryService = MemberInquiryService(
            findMemberPort, signInMemberPort, authenticationManage, jwtTokenProvider
        )

        val defaultMember = Member(
            id = "user",
            password = "pass",
            name = "kim"
        )

        Given("Member 조회") {
            When("userId를 기반으로 조회할 때") {

                every { findMemberPort.findMemberByUserId("user") } returns defaultMember

                val memberFinded = memberInquiryService.findMemberByMemberId("user")
                Then("멤버 정보가 일치 해야한다") {
                    memberFinded!!.id shouldBe "user"
                    memberFinded.password shouldBe "pass"
                    memberFinded.name shouldBe "kim"
                }
            }
        }

        Given("인증 시도") {
            When("인증 시도 실패") {
                every { authenticationManage.authenticate(any()) } throws Exception()
                Then("Exception 발생") {
                    shouldThrow<MemberException> {
                        memberInquiryService.signIn("", "")
                    }
                }
            }

            When("인증 시도 성공") {
                every { authenticationManage.authenticate(any()) } returns mockk()
                Then("멤버 정보가 등록 안된 경우") {
                    every { findMemberPort.findMemberByUserId("user") } returns null
                    shouldThrow<MemberException> {
                        memberInquiryService.signIn("user", "pass")
                    }
                }
                Then("멤버 정보가 등록 된 경우") {
                    every { findMemberPort.findMemberByUserId("user") } returns defaultMember
                    every { jwtTokenProvider.createToken(any()) } returns "token"
                    every { signInMemberPort.signInMember(any()) } returns mockk()
                    val member = memberInquiryService.signIn("user", "")
                    member.token shouldBe "token"
                }
            }
        }
    }
}