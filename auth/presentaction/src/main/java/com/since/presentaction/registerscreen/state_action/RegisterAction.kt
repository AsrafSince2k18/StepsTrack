package com.since.presentaction.registerscreen.state_action

sealed interface RegisterAction {

    data object LoginScreen : RegisterAction

    data object RegisterBtn: RegisterAction

    data object PasswordToggle: RegisterAction

}