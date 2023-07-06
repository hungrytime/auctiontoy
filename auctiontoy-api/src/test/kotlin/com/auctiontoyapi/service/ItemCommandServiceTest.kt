package com.auctiontoyapi.service

import com.auctiontoyapi.adapter.out.port.KafkaProducer
import com.auctiontoyapi.adapter.out.vo.BidItemVO
import com.auctiontoyapi.adapter.out.vo.ItemModifyVO
import com.auctiontoyapi.application.port.out.FindItemPort
import com.auctiontoyapi.application.port.out.FindMemberPort
import com.auctiontoyapi.application.port.out.SaveItemPort
import com.auctiontoydomain.Member
import com.auctiontoydomain.entity.Item
import com.auctiontoydomain.entity.ItemStatus
import com.auctiontoydomain.exception.MemberException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.every
import io.mockk.mockk
import java.math.BigDecimal
import java.time.LocalDateTime

internal class ItemCommandServiceTest : BehaviorSpec() {
    override fun isolationMode(): IsolationMode = IsolationMode.SingleInstance

    init {
        val saveItemPort = mockk<SaveItemPort>()
        val kafkaProducer = mockk<KafkaProducer>()
        val findItemPort = mockk<FindItemPort>()
        val findMemberPort = mockk<FindMemberPort>()
        val itemCommandService = ItemCommandService(
            saveItemPort, kafkaProducer, findItemPort, findMemberPort
        )

        val defaultPrepareItem = Item(
            1, 2, "defaultItem", ItemStatus.PREPARE_AUCTION, BigDecimal(1000), BigDecimal(2000), BigDecimal(4000),
            BigDecimal(3000), BigDecimal(1500), 0L, "kim", LocalDateTime.now(), LocalDateTime.now()
        )

        val defaultActiveItem = Item(
            1, 1, "defaultItem", ItemStatus.ACTIVE_AUCTION, BigDecimal(1000), BigDecimal(2000), BigDecimal(4000),
            BigDecimal(3000), BigDecimal(1500), 0L, "kim", LocalDateTime.now(), LocalDateTime.now()
        )

        val defaultActiveItem2 = Item(
            1, 2, "defaultItem", ItemStatus.ACTIVE_AUCTION, BigDecimal(1000), BigDecimal(2000), BigDecimal(4000),
            BigDecimal(3000), BigDecimal(1500), 0L, "kim", LocalDateTime.now(), LocalDateTime.now()
        )

        Given("아이템 등록 및 수정"){
            When("상품 수정 시") {
                val modifyItem = ItemModifyVO(
                    1, "modifyItem", BigDecimal(1000), BigDecimal(3000),
                BigDecimal(2000), LocalDateTime.now(), LocalDateTime.now())

                Then("상품의 상태가 경매 시작전이 아닌 경우") {
                    every { findItemPort.findItemByItemId(1) } returns defaultActiveItem
                    shouldThrow<IllegalArgumentException> {
                        itemCommandService.modify(modifyItem)
                    }
                }

                Then("아이템을 정상적으로 수정 가능한 경우") {
                    every { findItemPort.findItemByItemId(1) } returns defaultPrepareItem
                    every { saveItemPort.save(defaultPrepareItem) } returns mockk()
                    itemCommandService.modify(modifyItem)
                }
            }
        }

        Given("입찰") {
            When("입찰을 시도 할 때") {
                val tryBidInfo = BidItemVO(1,1, BigDecimal(3000))

                Then("찾는 아이템이 없는 경우") {
                    every { findItemPort.findItemByItemId(1) } returns null
                    shouldThrow<Exception> {
                        itemCommandService.bid(tryBidInfo)
                    }
                }

                Then("입찰 시도하는 멤버가 존재하지 않는 경우") {
                    every { findItemPort.findItemByItemId(1) } returns defaultPrepareItem
                    every { findMemberPort.findByMemberId(tryBidInfo.memberId) } returns null

                    shouldThrow<IllegalArgumentException> {
                        itemCommandService.bid(tryBidInfo)
                    }
                }

                Then("입찰 시도하는 사람이 본인이 경우 입찰을 할 수 없다") {
                    every { findItemPort.findItemByItemId(1) } returns defaultActiveItem
                    every { findMemberPort.findByMemberId(tryBidInfo.memberId) } returns mockk()

                    shouldThrow<MemberException> {
                        itemCommandService.bid(tryBidInfo)
                    }
                }

                Then("입찰 시도하는 아이템 상태가 ACTIVE가 아닌 경우") {
                    every { findItemPort.findItemByItemId(1) } returns defaultPrepareItem
                    every { findMemberPort.findByMemberId(tryBidInfo.memberId) } returns mockk()

                    shouldThrow<IllegalArgumentException> {
                        itemCommandService.bid(tryBidInfo)
                    }
                }

                Then("성공적으로 입찰을 하는 경우") {
                    val member = Member(2,"user", "pass", "kim")

                    every { findItemPort.findItemByItemId(1) } returns defaultActiveItem2
                    every { findMemberPort.findByMemberId(tryBidInfo.memberId) } returns mockk()
                    every { findMemberPort.findByMemberId(defaultActiveItem2.memberId) } returns member
                    every { saveItemPort.save(defaultActiveItem2) } returns mockk()
                    every { saveItemPort.saveBid(defaultActiveItem2, 2, tryBidInfo.itemPrice) } returns mockk()

                    itemCommandService.bid(tryBidInfo)
                }

            }
        }
    }
}