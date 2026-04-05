package com.since.auth.domain.user_validator

interface PatternValidator {

    fun patternValidator(email: String) : Boolean

}