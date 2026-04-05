package com.since.run.location.di

import com.since.run.domain.repo.LocationObserve
import com.since.run.location.LocationObserveImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val runLocationModule = module {
    singleOf(::LocationObserveImpl).bind<LocationObserve>()
}