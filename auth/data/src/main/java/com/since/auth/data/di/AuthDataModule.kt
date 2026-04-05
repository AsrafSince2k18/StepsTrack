package com.since.auth.data.di

import com.since.auth.data.impl.AuthRepoImpl
import com.since.auth.data.impl.PatternValidatorImpl
import com.since.auth.domain.auth_repo.AuthRepo
import com.since.auth.domain.user_validator.PatternValidator
import com.since.auth.domain.user_validator.UserDataValidate
import org.koin.dsl.module

val authDataModule = module {

    single<PatternValidator> {
        PatternValidatorImpl
    }

    single {
        UserDataValidate(patternValidator = get())
    }

    single<AuthRepo> {
        AuthRepoImpl(httpClient = get(), sessionRepo = get())
    }

}