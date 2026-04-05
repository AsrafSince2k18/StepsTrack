package com.since.auth.domain.user_validator

data class PasswordValidate(

    val isLength: Boolean=false,
    val isNumber: Boolean=false,
    val isUpperCase: Boolean=false,
    val isLowerCase: Boolean=false
){
    val validPassword
        get() = isLength&&isNumber&&isUpperCase&&isLowerCase
}
