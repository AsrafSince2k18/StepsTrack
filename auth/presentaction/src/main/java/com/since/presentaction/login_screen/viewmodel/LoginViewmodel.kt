package com.since.presentaction.login_screen.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.since.auth.domain.auth_repo.AuthRepo
import com.since.auth.domain.user_validator.UserDataValidate
import com.since.core.domain.error.Result
import com.since.presentaction.login_screen.event.LoginEvent
import com.since.presentaction.login_screen.state_action.LoginAction
import com.since.presentaction.login_screen.state_action.LoginState
import com.since.presentaction.ui.ui_error.asUiError
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class LoginViewmodel(
    private val userDataValidate: UserDataValidate,
    private val authRepo: AuthRepo
) : ViewModel() {

    var state by mutableStateOf(LoginState())
        private set

   private val _channel = Channel<LoginEvent>()
    val channel = _channel.receiveAsFlow()

    init {
        combine(
            state.email.run { snapshotFlow { text } },
            state.password.run { snapshotFlow { text } }
        ){email,password->
            val emailValid = userDataValidate.emailValid(email.toString().trim())

            state=state.copy(
                emailValid = emailValid,
                btnEnable = !state.btnLoading && emailValid && password.length>3

            )
        }.launchIn(viewModelScope)

    }

    fun onAction(action: LoginAction){
        when(action){
            LoginAction.LoginBtn -> {
                loginUser()
            }
            LoginAction.PasswordToggle -> {
                state=state.copy(
                    passwordVisible = !state.passwordVisible
                )
            }
            else -> Unit
        }
    }

    private fun loginUser(){
        viewModelScope.launch {
            state=state.copy(btnLoading = true)
            val result = authRepo.loginUser(
                email = state.email.text.toString().trim(),
                password = state.password.text.toString()
            )
            state=state.copy(btnLoading = false)

            when(result){
                is Result.Error -> {
                    _channel.send(LoginEvent.Error(error = result.error.asUiError()))
                }
                is Result.Success -> {
                    _channel.send(LoginEvent.Success)
                }
            }

        }
    }

}