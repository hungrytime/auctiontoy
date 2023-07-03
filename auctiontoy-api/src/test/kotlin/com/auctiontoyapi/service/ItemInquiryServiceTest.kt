package com.auctiontoyapi.service

import com.auctiontoyapi.adapter.`in`.common.page.PageContent
import com.auctiontoyapi.adapter.out.vo.BidItemVO
import com.auctiontoyapi.adapter.out.vo.SellerItemListVO
import com.auctiontoyapi.application.port.out.FindItemPort
import com.auctiontoydomain.entity.Item
import com.auctiontoydomain.entity.ItemStatus
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import java.math.BigDecimal
import java.time.LocalDateTime

internal class ItemInquiryServiceTest: BehaviorSpec() {
    override fun isolationMode(): IsolationMode = IsolationMode.SingleInstance

    init {
        val findItemPort = mockk<FindItemPort>()
        val itemInquiryService = ItemInquiryService(findItemPort)

        val defaultItem = Item(
            1, 1, "defaultItem", ItemStatus.PREPARE_AUCTION, BigDecimal(1000), BigDecimal(2000), BigDecimal(4000),
            BigDecimal(3000), BigDecimal(1500), 0L, "kim", LocalDateTime.now(), LocalDateTime.now()
        )

        Given("MemberId가 주어졌을 때") {

            val pageable = PageRequest.of(0, 10)
            val bidItems = listOf<BidItemVO>(BidItemVO(1,1, BigDecimal(1000)))
            val page = PageImpl(mutableListOf(defaultItem), pageable, 1)

            When("자신이 등록한 아이템을 조회할 때") {
                every { findItemPort.findItemListByMemberId(1, pageable) } returns page
                itemInquiryService.findItemListForSeller(1, pageable)
            }

            When("참가자 입장에서 아이템 정보를 조회할 때") {
                Then("특별한 조건없이 조회") {
                    every { findItemPort.findItemList( pageable) } returns page
                    every { findItemPort.findParticipationBid(1, listOf(1))}  returns bidItems
                    itemInquiryService.findItemList(1, pageable)
                }
                Then("등록된 날짜를 기준으로 조회") {
                    every { findItemPort.findItemListByCreatedAt(any(), any(), pageable) } returns page
                    every { findItemPort.findParticipationBid(1, listOf(1))}  returns bidItems
                    itemInquiryService.findItemListByCreatedAt(1, "2023-06-20", "2023-06-30", pageable)
                }
                Then("아이템 상태를 기준으로 조회") {
                    every { findItemPort.findItemListByStatus("ACTIVE_AUCTION", pageable) } returns page
                    every { findItemPort.findParticipationBid(1, listOf(1))}  returns bidItems
                    itemInquiryService.findItemListByStatus(1, "ACTIVE_AUCTION", pageable)
                }
                Then("경매 아이템 목록을 memberId 기준으로 조회") {
                    every { findItemPort.findBidItemListByMemberId(1, pageable) } returns page
                    itemInquiryService.findBidItemListByMemberId(1, pageable)
                }
            }


        }

        Given("ItemId가 주어졌을 때") {
            When("아이템의 상세 정보를 조회할 때") {
                every { findItemPort.findItemByItemId(1) } returns defaultItem
                itemInquiryService.findItemDetailForSeller(1).itemName shouldBe "defaultItem"
            }
            When("아이템 아이디를 기준으로 정보 조회") {
                every { findItemPort.findItemByItemId(1) } returns defaultItem
                itemInquiryService.findByItemId("1")
            }
            //TODO 좀 더 다듬을 필요가 있음 레디스 기준이 바뀔지도...
            When("아이템 아이디를 기준으로 레디스에서 조회") {
                every { findItemPort.findByItemIdInRedis("1") } returns defaultItem
                itemInquiryService.findByItemIdInRedis("1")
            }
        }
    }
}