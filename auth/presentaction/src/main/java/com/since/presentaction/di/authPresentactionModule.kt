package com.since.presentaction.di

import com.since.presentaction.login_screen.viewmodel.LoginViewmodel
import com.since.presentaction.registerscreen.viewmodel.RegisterViewmodel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val authPresentactionModule = module {


    viewModelOf(::LoginViewmodel)
    viewModelOf(::RegisterViewmodel)

}