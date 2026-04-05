package com.since.core.data.di

import com.since.core.data.impl.SessionRepoImpl
import com.since.core.data.network.utility.HttpClientFactory
import com.since.core.domain.auth_network.SessionRepo
import io.ktor.client.HttpClient
import org.koin.dsl.module

val coreDataModule = module {

    single<SessionRepo> {
        SessionRepoImpl(sharedPreferences = get())
    }

    single<HttpClient> {
        HttpClientFactory(sessionRepo = get()).httpClientFactory()
    }

}