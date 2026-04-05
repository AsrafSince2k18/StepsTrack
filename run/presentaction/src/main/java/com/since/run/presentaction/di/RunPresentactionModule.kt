package com.since.run.presentaction.di

import com.since.run.presentaction.active_run.viewmodel.ActiveRunViewModel
import com.since.run.presentaction.over_view.viewmodel.OverViewViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val runPresentationModule= module {
    viewModelOf(::OverViewViewModel)
    viewModelOf(::ActiveRunViewModel)
}