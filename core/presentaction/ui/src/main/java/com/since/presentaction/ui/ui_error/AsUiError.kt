package com.since.presentaction.ui.ui_error

import com.since.core.domain.error.DataError
import com.since.presentaction.ui.R

fun DataError.NetworkError.asUiError(): UiError{
    return when(this){
        DataError.NetworkError.NO_INTERNET -> {
            UiError.StringResource(R.string.no_internet)
        }
        DataError.NetworkError.NOT_FOUND -> {
            UiError.StringResource(R.string.not_found)
        }
        DataError.NetworkError.BAD_REQUEST -> {
            UiError.StringResource(R.string.bad_request)
        }
        DataError.NetworkError.SERVER_ERROR -> {
            UiError.StringResource(R.string.server_error)
        }
        DataError.NetworkError.KOTLINX_SEREARLIZATION -> {
            UiError.StringResource(R.string.kotlinx_seralization)
        }
        DataError.NetworkError.UN_AUTHORIZED -> {
            UiError.StringResource(R.string.un_authorized)
        }
        else -> {
            UiError.StringResource(R.string.un_know)
        }
    }
}