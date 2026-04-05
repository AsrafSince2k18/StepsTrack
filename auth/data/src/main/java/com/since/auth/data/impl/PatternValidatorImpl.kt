package com.since.auth.data.impl

import android.util.Patterns
import com.since.auth.domain.user_validator.PatternValidator

object PatternValidatorImpl : PatternValidator {
    override fun patternValidator(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}