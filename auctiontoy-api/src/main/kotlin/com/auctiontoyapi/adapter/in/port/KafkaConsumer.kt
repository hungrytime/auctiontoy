package com.auctiontoyapi.adapter.`in`.port

import mu.KLogging
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

@Service
class KafkaConsumer {

    @KafkaListener(topics = ["test1"], groupId = "foo")
    fun consumer(message: String) {
        logger.info("consumer : $message")
    }

    companion object : KLogging()
}