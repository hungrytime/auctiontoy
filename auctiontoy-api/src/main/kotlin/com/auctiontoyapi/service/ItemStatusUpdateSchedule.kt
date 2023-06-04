package com.auctiontoyapi.service

import mu.KLogging
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class ItemStatusUpdateSchedule {

    @Scheduled(cron = "0 0 0/1 * * *")
    fun run() {
        logger.info("스케쥴 진짜 되니?")
    }

    companion object : KLogging()
}