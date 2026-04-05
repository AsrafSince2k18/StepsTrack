package com.since.core.data.network.utility

import com.since.core.data.BuildConfig
import com.since.core.data.model.AuthInfoSerelizable
import com.since.core.data.model.RefreshTokenRequest
import com.since.core.domain.auth_network.SessionRepo
import com.since.core.domain.auth_network.model.AuthInfo
import com.since.core.domain.error.Result
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import timber.log.Timber

class HttpClientFactory(
    private val sessionRepo: SessionRepo
) {

     fun httpClientFactory() : HttpClient{
        return HttpClient(CIO){

            install(ContentNegotiation){
                json(
                    json = Json {
                        ignoreUnknownKeys=true
                    }
                )
            }

            install(Logging){
                level = LogLevel.ALL
                logger = object : Logger{
                    override fun log(message: String) {
                        Timber.i(message)
                    }
                }
            }


            defaultRequest {
                contentType(ContentType.Application.Json)
                header("x-api-key", BuildConfig.API_KEY)
                url(BuildConfig.BASE_URL)
            }


            install(Auth){
                bearer {
                    loadTokens {
                        val getAuth = sessionRepo.getAuthInfo()
                        BearerTokens(
                            accessToken = getAuth?.accessToken ?: "",
                            refreshToken = getAuth?.refreshToken ?:""
                        )
                    }

                    refreshTokens {

                        val getAuth = sessionRepo.getAuthInfo()

                        val refreshTokenRequest = client.post<RefreshTokenRequest, AuthInfoSerelizable>(
                            url="/auth/refresh",
                            body = RefreshTokenRequest(
                                refreshToken = getAuth?.refreshToken ?: ""
                            )
                        )

                        if(refreshTokenRequest is Result.Success){
                            val newAuth = refreshTokenRequest.success

                            sessionRepo.setAuthInfo(authInfo = AuthInfo(
                                accessToken = newAuth.accessToken,
                                refreshToken = newAuth.refreshToken
                            ))
                            BearerTokens(
                                accessToken = newAuth.accessToken,
                                refreshToken = newAuth.refreshToken
                            )
                        }else{
                            BearerTokens(
                                accessToken ="",
                                refreshToken = ""
                            )
                        }

                    }

                }
            }

        }
    }

}