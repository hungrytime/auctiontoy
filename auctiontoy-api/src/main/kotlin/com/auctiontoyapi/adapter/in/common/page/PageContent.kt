package com.auctiontoyapi.adapter.`in`.common.page

data class PageContent<T> (
    val page: Int,
    val totalPage: Int,
    val contents: T
) {
    companion object {
        fun <T> toPage(page: Int, size: Int, totalSize: Int, contents: T): PageContent<T> {
            if (size == 0) return PageContent(0,0, contents)
            val totalPage = totalSize / size + if (totalSize % size != 0) 1 else 0
            return PageContent(page, totalPage, contents)
        }
    }
}
