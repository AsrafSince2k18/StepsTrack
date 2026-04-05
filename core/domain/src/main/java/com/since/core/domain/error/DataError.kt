package com.since.core.domain.error

sealed interface DataError : Error {

    enum class NetworkError : DataError{
        NO_INTERNET,
        NOT_FOUND,
        BAD_REQUEST,
        UN_AUTHORIZED,
        SERVER_ERROR,
        KOTLINX_SEREARLIZATION,
        CONFLICT,
        UN_KNOW
    }



}




