package com.auctiontoyapi.application.port.out

interface LogoutPort {
    fun logout(token: String)
}