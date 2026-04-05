package com.since.run.presentaction.over_view.event

import com.since.presentaction.ui.ui_error.UiError

sealed interface OverViewEvent {


    data class Error(val error: UiError) : OverViewEvent

    data object Success  : OverViewEvent

}