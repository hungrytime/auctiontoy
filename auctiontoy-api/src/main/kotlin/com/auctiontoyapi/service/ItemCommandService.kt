package com.auctiontoyapi.service

import com.auctiontoyapi.adapter.out.vo.ItemVO
import com.auctiontoyapi.application.port.`in`.RegisterItemUseCase
import com.auctiontoyapi.application.port.out.RegisterItemPort
import org.springframework.stereotype.Service

@Service
class ItemCommandService(
    private val registerItemPort: RegisterItemPort
): RegisterItemUseCase {
    override fun register(item: ItemVO) {
        registerItemPort.register(item)
    }
}