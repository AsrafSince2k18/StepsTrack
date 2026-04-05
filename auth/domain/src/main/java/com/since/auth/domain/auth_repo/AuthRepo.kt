package com.since.auth.domain.auth_repo

import com.since.core.domain.error.DataError
import com.since.core.domain.error.EmptyResult

interface AuthRepo {


    suspend fun registerUser(name:String,
                             email:String,
                             password: String) : EmptyResult<DataError.NetworkError>


    suspend fun loginUser(
        email:String,
        password: String
    ): EmptyResult<DataError.NetworkError>

}