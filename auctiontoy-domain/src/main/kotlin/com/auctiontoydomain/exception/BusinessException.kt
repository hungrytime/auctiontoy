package com.auctiontoydomain.exception

import com.auctiontoydomain.exception.enum.ResultCode

class BusinessException(
    val resultCode: ResultCode,
    override val message: String?,
    override val cause: Throwable? = null,
    val content: Any? = null
): RuntimeException(message, cause)