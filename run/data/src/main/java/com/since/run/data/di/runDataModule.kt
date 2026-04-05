package com.since.run.data.di

import com.since.run.data.active_run.repo_impl.NetworkObserveRepoImpl
import com.since.run.data.overview.repo_impl.OverViewRepoImpl
import com.since.run.domain.activerun_screen.TrackerData
import com.since.run.domain.overview_screen.OverviewRepo
import com.since.run.domain.repo.NetworkObserveRepo
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val runDataModule = module {
    singleOf(::OverViewRepoImpl).bind<OverviewRepo>()

    singleOf(::NetworkObserveRepoImpl).bind<NetworkObserveRepo>()

    single {
        TrackerData(locationObserve = get(),applicationScope = get())
    }

}