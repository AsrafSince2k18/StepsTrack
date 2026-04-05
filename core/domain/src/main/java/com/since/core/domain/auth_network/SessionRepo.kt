package com.since.core.domain.auth_network

import com.since.core.domain.auth_network.model.AuthInfo

interface SessionRepo {

    suspend fun setAuthInfo(authInfo: AuthInfo?)

    suspend fun getAuthInfo() : AuthInfo?

}