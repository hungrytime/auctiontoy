package com.auctiontoyapi.adapter.`in`.common.dto

import com.auctiontoydomain.exception.enum.ResultCode

data class PageResponseDTO<T> (
    val code: String = "",
    val message: String? = null,
    val content: T? = null,
    val page: Int,
    val totalPage: Int
) {
    companion object {
        fun <T> success(content: T? = null, page: Int, totalPage: Int): PageResponseDTO<T> {
            return PageResponseDTO(
                code = ResultCode.SUCCESS.name,
                content = content,
                page = page,
                totalPage = totalPage
            )
        }

        fun <T> fail(
            code: ResultCode,
            message: String? = null,
            content: T? = null,
            page: Int,
            totalPage: Int
        ): PageResponseDTO<T> {
            return PageResponseDTO(
                code = code.name,
                message = message,
                content = content,
                page = page,
                totalPage = totalPage
            )
        }
    }
}