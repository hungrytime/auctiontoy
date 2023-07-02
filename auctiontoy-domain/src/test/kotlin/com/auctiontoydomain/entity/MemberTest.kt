package com.auctiontoydomain.entity

import com.auctiontoydomain.Member
import com.auctiontoydomain.MemberStatus
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import java.time.LocalDateTime

internal class MemberTest: BehaviorSpec() {
    override fun isolationMode(): IsolationMode = IsolationMode.SingleInstance

    init {
        val defaultMemberInfo = Member(
            1,
            "user",
            "pass",
            "kim",
            MemberStatus.ACTIVATED
        )

        Given("탈퇴한 경우") {
            defaultMemberInfo.changeRevoked()
            Then("회원 상태가 REVOKED로 바뀌어야된다") {
                defaultMemberInfo.memberStatus shouldBe MemberStatus.REVOKED
            }
        }
    }



}