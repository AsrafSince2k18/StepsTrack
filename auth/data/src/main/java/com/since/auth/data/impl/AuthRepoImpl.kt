package com.since.auth.data.impl

import com.since.auth.data.model.LoginRequest
import com.since.auth.data.model.RegisterRequest
import com.since.auth.domain.auth_repo.AuthRepo
import com.since.core.data.model.AuthInfoSerelizable
import com.since.core.data.network.utility.post
import com.since.core.domain.auth_network.SessionRepo
import com.since.core.domain.auth_network.model.AuthInfo
import com.since.core.domain.error.DataError
import com.since.core.domain.error.EmptyResult
import com.since.core.domain.error.Result
import com.since.core.domain.error.asEmptyResult
import io.ktor.client.HttpClient

class AuthRepoImpl(
    private val httpClient: HttpClient,
    private val sessionRepo: SessionRepo
) : AuthRepo {


    override suspend fun registerUser(
        name: String,
        email: String,
        password: String
    ): EmptyResult<DataError.NetworkError> {
        val request = httpClient.post<RegisterRequest,Unit>(
            url = "/auth/signup",
            body = RegisterRequest(
                name=name,
                email=email,
                password = password
            )
        )
        return request
    }

    override suspend fun loginUser(
        email: String,
        password: String
    ): EmptyResult<DataError.NetworkError> {
        val loginRequest = httpClient.post<LoginRequest, AuthInfoSerelizable>(
            url = "/auth/signin",
            body = LoginRequest(
                email = email,
                password=password
            )
        )

        if(loginRequest is Result.Success){
            sessionRepo.setAuthInfo(AuthInfo(
                accessToken = loginRequest.success.accessToken,
                refreshToken = loginRequest.success.refreshToken
            ))
        }
        return loginRequest.asEmptyResult()
    }
}