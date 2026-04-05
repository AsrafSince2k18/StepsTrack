package com.since.presentaction.registerscreen.event

import com.since.presentaction.ui.ui_error.UiError

sealed interface RegisterEvent {

    data class Error(val error: UiError) : RegisterEvent
    data object Success: RegisterEvent

}