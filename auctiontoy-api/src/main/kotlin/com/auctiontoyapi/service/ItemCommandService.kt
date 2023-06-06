package com.auctiontoyapi.service

import com.auctiontoyapi.adapter.out.port.KafkaProducer
import com.auctiontoyapi.adapter.out.vo.BidItemVO
import com.auctiontoyapi.adapter.out.vo.ItemVO
import com.auctiontoyapi.application.port.`in`.BidItemUseCase
import com.auctiontoyapi.application.port.`in`.RegisterItemUseCase
import com.auctiontoyapi.application.port.out.FindItemPort
import com.auctiontoyapi.application.port.out.RegisterItemPort
import org.springframework.stereotype.Service

@Service
class ItemCommandService(
    private val registerItemPort: RegisterItemPort,
    private val kafkaProducer: KafkaProducer,
    private val findItemPort: FindItemPort
): RegisterItemUseCase, BidItemUseCase {
    override fun register(item: ItemVO) {
        registerItemPort.register(item)
    }

    override fun tryBid(msg: String) {
        kafkaProducer.sendMessage(msg)
    }

    override fun bid(tryItem: BidItemVO) {
        val item = findItemPort.findItemByItemId(tryItem.itemId) ?: throw Exception("존재하는 아이템 아이디가 없습니다 아이템 아이디 : ${tryItem.itemId}")
        if (item.checkValidPrice(tryItem.itemPrice).not()) return
        item.changeRealTimePrice(tryItem.itemPrice)
        registerItemPort.save(item)
    }
}