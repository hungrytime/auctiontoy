package com.auctiontoyapi.adapter.`in`.port

import com.auctiontoyapi.adapter.out.vo.BidItemVO
import com.auctiontoyapi.application.port.`in`.BidItemUseCase
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import mu.KLogging
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class KafkaConsumer(
    private val bidItemUseCase: BidItemUseCase
) {

    @KafkaListener(topics = ["trybid"], groupId = "bid")
    fun consumerEndBid(message: String) {
        val bidItem: BidItemVO = jacksonObjectMapper().readValue(message, BidItemVO::class.java)
        bidItemUseCase.bid(bidItem)
    }



    companion object : KLogging()
}