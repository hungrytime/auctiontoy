package com.auctiontoyapi.service

import com.auctiontoyapi.adapter.out.port.ItemCommandAdapter
import com.auctiontoyapi.adapter.out.port.ItemInquiryAdapter
import mu.KLogging
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ItemStatusUpdateSchedule(
    private val itemInquiryAdapter: ItemInquiryAdapter,
    private val itemCommandAdapter: ItemCommandAdapter
) {

    @Scheduled(cron = "0 0 0/1 * * *")
    @Transactional
    fun updatePrepareToActive() {
        val itemList = itemInquiryAdapter.findByItemStatusAndStartDate("PREPARE_AUCTION")
        itemList.map { it.makeActiveStatus() }
        itemCommandAdapter.saveAll(itemList)
    }

    @Scheduled(cron = "0 0 0/1 * * *")
    @Transactional
    fun updateActiveToEnd() {
        val itemList = itemInquiryAdapter.findByItemStatusAndEndDate("ACTIVE_AUCTION")

        val end = itemList.filter { it.minimumPrice <= it.realTimePrice }
        val fail = itemList.filter { it.minimumPrice > it.realTimePrice }

        end.map { it.makeEndStatus() }
        fail.map { it.makeFailStatus() }

        itemCommandAdapter.saveAll(fail)
        itemCommandAdapter.saveAll(end)
    }

    companion object : KLogging()
}