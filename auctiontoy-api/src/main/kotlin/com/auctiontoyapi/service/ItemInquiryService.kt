package com.auctiontoyapi.service

import com.auctiontoyapi.adapter.out.vo.ItemVO
import com.auctiontoyapi.application.port.`in`.FindItemUseCase
import com.auctiontoyapi.application.port.out.FindItemPort
import org.springframework.stereotype.Service

@Service
class ItemInquiryService(
    private val findItemPort: FindItemPort
) : FindItemUseCase {
    override fun findItemListByMemberId(memberId: Long): List<ItemVO> {
        return findItemPort.findItemListByMemberId(memberId).map { ItemVO.from(it) }
    }

    override fun findByItemId(itemId: String): ItemVO? {
        return findItemPort.findItemByItemId(itemId.toLong())?.let { ItemVO.from(it) }
    }

    override fun findByItemIdInRedis(itemId: String): ItemVO? {
        return findItemPort.findByItemIdInRedis(itemId)?.let { ItemVO.from(it) }
    }
}