package com.auctiontoydomain.exception

import com.auctiontoydomain.exception.enum.ResultCode

sealed class BusinessException(
    open val resultCode: ResultCode,
    override val message: String?,
    override val cause: Throwable? = null,
    open val content: Any? = null
): RuntimeException(message, cause)