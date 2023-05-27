package com.auctiontoyapi.common.advice

import com.auctiontoyapi.adapter.`in`.common.dto.ResponseDTO
import com.auctiontoydomain.exception.BusinessException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class AuctionToyApiAdvice {

    @ExceptionHandler(BusinessException::class)
    @ResponseStatus(HttpStatus.OK)
    fun exceptionHandler(e: BusinessException): ResponseDTO<String> {
        return ResponseDTO.fail(e.resultCode, content = "error", message = e.message)
    }

    @ExceptionHandler(Exception::class)
    fun exceptionHandler(e: Exception): String? {
        return e.message
    }
}