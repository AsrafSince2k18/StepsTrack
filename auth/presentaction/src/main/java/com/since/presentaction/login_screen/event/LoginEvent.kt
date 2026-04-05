package com.since.presentaction.login_screen.event

import com.since.presentaction.ui.ui_error.UiError

sealed interface LoginEvent {


    data class Error(val error: UiError) : LoginEvent

    data object Success : LoginEvent

}