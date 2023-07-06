package com.auctiontoyapi.adapter.`in`.dto

import com.auctiontoyapi.adapter.`in`.common.page.PageParam

// 날짜 범위로 검색하고 Paging 하는 DTO
data class SearchDateWithPageDTO(
    val memberId: Long,
    val startDate: String,
    val endDate: String,
    override val page: Int,
    override val size: Int,
    override val totalPage: Int
) : PageParam(page, size, totalPage)
