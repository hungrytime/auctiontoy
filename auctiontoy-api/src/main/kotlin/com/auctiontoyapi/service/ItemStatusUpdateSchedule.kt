package com.auctiontoyapi.service

import com.auctiontoyapi.adapter.out.port.ItemCommandAdapter
import com.auctiontoyapi.adapter.out.port.ItemInquiryAdapter
import mu.KLogging
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class ItemStatusUpdateSchedule(
    private val itemInquiryAdapter: ItemInquiryAdapter,
    private val itemCommandAdapter: ItemCommandAdapter
) {

    @Scheduled(cron = "0 0 0/1 * * *")
    fun updatePrepareToActive() {
        val itemList = itemInquiryAdapter.findByItemStatusAndStartDate("PREPARE_AUCTION")
        itemList.map { it.makeActiveStatus() }
        itemCommandAdapter.saveAll(itemList)
    }

    @Scheduled(cron = "0 0 0/1 * * *")
    fun updateActiveToEnd() {
        val itemList = itemInquiryAdapter.findByItemStatusAndStartDate("ACTIVE_AUCTION")
        itemList.map { it.makeEndStatus() }
        itemCommandAdapter.saveAll(itemList)
    }

    companion object : KLogging()
}