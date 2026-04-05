package com.since.auth.domain.user_validator

class UserDataValidate(
    private val patternValidator: PatternValidator
) {

    companion object{
        const val PASSWORD_LENGTH=9
    }


    val emailValid : (email:String) -> Boolean ={email->
        patternValidator.patternValidator(email = email)
    }


    val passwordValid : (password:String) -> PasswordValidate = {password->

        val isLength = password.length >= PASSWORD_LENGTH
        val isNumber = password.any { it.isDigit() }
        val isUpperCase = password.any { it.isUpperCase() }
        val isLowerCase = password.any { it.isLowerCase() }
        PasswordValidate(
            isLength = isLength,
            isNumber = isNumber,
            isUpperCase = isUpperCase,
            isLowerCase = isLowerCase
        )
    }

}