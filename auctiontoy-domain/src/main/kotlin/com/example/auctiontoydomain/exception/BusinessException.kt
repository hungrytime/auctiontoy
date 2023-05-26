package com.example.auctiontoydomain.exception

import com.example.auctiontoydomain.exception.enum.ResultCode

class BusinessException(
    val resultCode: ResultCode,
    override val message: String?,
    override val cause: Throwable? = null,
    val content: Any? = null
): RuntimeException(message, cause)