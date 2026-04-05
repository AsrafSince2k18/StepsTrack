package com.since.presentaction.registerscreen.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.since.auth.domain.auth_repo.AuthRepo
import com.since.auth.domain.user_validator.PatternValidator
import com.since.auth.domain.user_validator.UserDataValidate
import com.since.core.domain.error.DataError
import com.since.core.domain.error.Result
import com.since.presentaction.designsystem.R
import com.since.presentaction.registerscreen.event.RegisterEvent
import com.since.presentaction.registerscreen.state_action.RegisterAction
import com.since.presentaction.registerscreen.state_action.RegisterState
import com.since.presentaction.ui.ui_error.UiError
import com.since.presentaction.ui.ui_error.asUiError
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class RegisterViewmodel(
    private val userDataValidate: UserDataValidate,
    private val authRepo: AuthRepo
): ViewModel() {

    var state by mutableStateOf(RegisterState())
        private set


    private val _channel = Channel<RegisterEvent>()
    val channel = _channel.receiveAsFlow()

    init {

        state.email.run {
            snapshotFlow { text }
        }.onEach {email->
            val emailValid = userDataValidate.emailValid(email.toString().trim())
            state=state.copy(
                emailValid = emailValid,
                btnEnable = emailValid && !state.btnLoading && state.passwordValidate.validPassword
            )
        }.launchIn(viewModelScope)


        state.password.run {
            snapshotFlow { text }
        }.onEach { password->
            val passwordValid = userDataValidate.passwordValid(password.toString())
            state=state.copy(
                passwordValidate = passwordValid,
                btnEnable = state.emailValid && !state.btnLoading && passwordValid.validPassword
            )
        }.launchIn(viewModelScope)

    }

    fun onAction(action: RegisterAction){
        when(action){
            RegisterAction.PasswordToggle -> {
                state=state.copy(
                    passwordVisible = !state.passwordVisible
                )
            }
            RegisterAction.RegisterBtn -> {
                registerBtn()
            }
            else -> Unit
        }
    }


    private fun registerBtn(){
        viewModelScope.launch {
            state=state.copy(btnLoading = true)
            val result = authRepo.registerUser(
                name=state.name.text.toString().trim(),
                email =state.email.text.toString().trim(),
                password = state.password.text.toString()
            )
            state=state.copy(btnLoading = false)

            when(result){
                is Result.Error<DataError.NetworkError> -> {
                    if(result.error == DataError.NetworkError.CONFLICT){
                        _channel.send(RegisterEvent.Error(UiError.StringResource(R.string.conflict)))
                    }else {
                        _channel.send(RegisterEvent.Error(error = result.error.asUiError()))
                    }
                }
                is Result.Success -> {
                    _channel.send(RegisterEvent.Success)
                }
            }

        }
    }

}