package com.since.core.domain.error

sealed interface Result<out D, out E: Error> {

    data class Success<out D>(val success:D)  : Result<D, Nothing>

    data class Error<out E: com.since.core.domain.error.Error>(val error:E) : Result<Nothing,E>
}



inline fun <D,E:Error,T> Result<D,E>.map(map:(D) -> T) : Result<T,E>{
    return when(this){

        is Result.Error -> {
            Result.Error(error = error)
        }
        is Result.Success -> {
            Result.Success(map(success))
        }
    }
}


typealias EmptyResult<E> = Result<Unit,E>


fun <D,E:Error> Result<D,E>.asEmptyResult() : EmptyResult<E>{
    return map {  }
}
