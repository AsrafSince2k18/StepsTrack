package com.since.stepstracker.main_config.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.since.core.domain.auth_network.SessionRepo
import com.since.stepstracker.main_config.state_action.MainScreenState
import kotlinx.coroutines.launch

class MainViewModel(
    private val sessionRepo: SessionRepo
) : ViewModel() {

    var state by mutableStateOf(MainScreenState())
        private set


    init {
        viewModelScope.launch {

            state=state.copy(isChecking = false)
            state=state.copy(logedIn = sessionRepo.getAuthInfo()!=null)
            state=state.copy(isChecking = true)

        }
    }

}