package com.since.presentaction.login_screen.state_action

import androidx.compose.foundation.text.input.TextFieldState

data class LoginState(

    val email: TextFieldState = TextFieldState(),
    val emailValid: Boolean=false,

    val password: TextFieldState = TextFieldState(),
    val passwordVisible: Boolean=false,

    val btnLoading: Boolean=false,
    val btnEnable: Boolean=false

)
