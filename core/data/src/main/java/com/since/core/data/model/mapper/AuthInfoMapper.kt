package com.since.core.data.model.mapper

import com.since.core.data.model.AuthInfoSerelizable
import com.since.core.domain.auth_network.model.AuthInfo

fun AuthInfoSerelizable.toAuthInfoDeseralizable() : AuthInfo{
    return AuthInfo(
        accessToken = accessToken,
        refreshToken=refreshToken
    )
}

fun AuthInfo.toAuthInfoSeralizable(): AuthInfoSerelizable{
    return AuthInfoSerelizable(
        accessToken=accessToken,
        refreshToken = refreshToken
    )
}