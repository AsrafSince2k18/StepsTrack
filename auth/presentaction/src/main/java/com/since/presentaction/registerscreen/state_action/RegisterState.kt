package com.since.presentaction.registerscreen.state_action

import androidx.compose.foundation.text.input.TextFieldState
import com.since.auth.domain.user_validator.PasswordValidate

data class RegisterState(

    val name : TextFieldState = TextFieldState(),
    val nameValid: Boolean=false,

    val email : TextFieldState = TextFieldState(),
    val emailValid: Boolean=false,

    val password : TextFieldState = TextFieldState(),
    val passwordValidate: PasswordValidate = PasswordValidate(),

    val passwordVisible: Boolean=false,
    val btnEnable: Boolean=false,
    val btnLoading: Boolean=false

)
