package com.auctiontoyapi.service

import com.auctiontoyapi.adapter.out.vo.ItemVO
import com.auctiontoyapi.application.port.`in`.FindItemListUseCase
import com.auctiontoyapi.application.port.out.FindItemPort
import org.springframework.stereotype.Service

@Service
class ItemInquiryService(
    private val findItemPort: FindItemPort
) : FindItemListUseCase {
    override fun findItemListByMemberId(memberId: Long): List<ItemVO> {
        return findItemPort.findItemListByMemberId(memberId).map { ItemVO.from(it) }
    }

    override fun testRedis(key: String): String? {
        return findItemPort.testRedis(key)
    }
}