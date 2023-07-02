package com.auctiontoyapi.service

import com.auctiontoyapi.adapter.out.port.ItemCommandAdapter
import com.auctiontoyapi.adapter.out.port.ItemInquiryAdapter
import com.auctiontoydomain.entity.Item
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.every
import io.mockk.mockk
import java.math.BigDecimal
import java.time.LocalDateTime

internal class ItemStatusUpdateScheduleTest: BehaviorSpec() {
    override fun isolationMode(): IsolationMode = IsolationMode.SingleInstance

    init {
        val itemInquiryAdapter = mockk<ItemInquiryAdapter>()
        val itemCommandAdapter = mockk<ItemCommandAdapter>()

        val itemStatusUpdateSchedule = ItemStatusUpdateSchedule(
            itemInquiryAdapter, itemCommandAdapter
        )

        Given("아이템 상태") {
            When("Active로 변할 때") {
                every { itemInquiryAdapter.findByItemStatusAndStartDate("PREPARE_AUCTION") } returns listOf()
                every { itemCommandAdapter.saveAll(any()) } returns mockk()
                itemStatusUpdateSchedule.updatePrepareToActive()
            }
            When("Fail or End 로 변할 때") {
                val failItem = Item.makeItem(
                    1, "failItem", BigDecimal(1000), BigDecimal(4000),
                    BigDecimal(3000), LocalDateTime.now(), LocalDateTime.now())

                val endItem = Item.makeItem(
                    1, "failItem", BigDecimal(1000), BigDecimal(4000),
                    BigDecimal(2000), LocalDateTime.now(), LocalDateTime.now())

                failItem.realTimePrice = BigDecimal(2500)
                endItem.realTimePrice = BigDecimal(2500)

                every { itemInquiryAdapter.findByItemStatusAndEndDate("ACTIVE_AUCTION") } returns listOf(
                    failItem,
                    endItem
                )

                every { itemCommandAdapter.saveAll(any()) } returns mockk()
                every { itemCommandAdapter.saveAll(any()) } returns mockk()

                itemStatusUpdateSchedule.updateActiveToEnd()
            }
        }
    }
}