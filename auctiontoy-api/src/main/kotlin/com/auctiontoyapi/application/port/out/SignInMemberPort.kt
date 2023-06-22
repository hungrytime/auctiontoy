package com.auctiontoyapi.application.port.out

interface SignInMemberPort {
    fun signInMember(token: String)
}