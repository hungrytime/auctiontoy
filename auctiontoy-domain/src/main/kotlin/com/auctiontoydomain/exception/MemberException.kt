package com.auctiontoydomain.exception

import com.auctiontoydomain.exception.enum.ResultCode

class MemberException(
    override val resultCode: ResultCode,
    override val message: String?,
    override val cause: Throwable? = null,
): BusinessException(resultCode, message)