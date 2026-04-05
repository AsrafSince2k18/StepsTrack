package com.since.presentaction.login_screen.state_action

sealed interface LoginAction {

    data object PasswordToggle: LoginAction

    data object LoginBtn : LoginAction

    data object RegisterScreen : LoginAction

}