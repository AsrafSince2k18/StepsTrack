package com.since.core.data.model

import kotlinx.serialization.Serializable

@Serializable
data class AuthInfoSerelizable(

    val accessToken:String,
    val refreshToken:String

)
