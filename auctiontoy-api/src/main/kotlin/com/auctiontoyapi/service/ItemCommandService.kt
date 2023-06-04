package com.auctiontoyapi.service

import com.auctiontoyapi.adapter.out.port.KafkaProducer
import com.auctiontoyapi.adapter.out.vo.ItemVO
import com.auctiontoyapi.application.port.`in`.BidItemUseCase
import com.auctiontoyapi.application.port.`in`.RegisterItemUseCase
import com.auctiontoyapi.application.port.out.RegisterItemPort
import org.springframework.stereotype.Service

@Service
class ItemCommandService(
    private val registerItemPort: RegisterItemPort,
    private val kafkaProducer: KafkaProducer
): RegisterItemUseCase, BidItemUseCase {
    override fun register(item: ItemVO) {
        registerItemPort.register(item)
    }

    override fun tryBid(msg: String) {
        kafkaProducer.sendMessage(msg)
    }
}