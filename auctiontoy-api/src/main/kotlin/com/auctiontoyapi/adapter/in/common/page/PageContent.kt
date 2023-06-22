package com.auctiontoyapi.adapter.`in`.common.page

data class PageContent (
    open val page: Int,
    open val totalPage: Int
) {
    companion object {
        fun toPage(page: Int, size: Int, totalSize: Int): PageContent {
            if (size == 0) return PageContent(0,0)
            val totalPage = totalSize / size + if (totalSize % size != 0) 1 else 0
            return PageContent(page, totalPage)
        }
    }
}
