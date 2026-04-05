package com.since.run.presentaction.over_view.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.since.core.domain.error.DataError
import com.since.core.domain.error.Result
import com.since.presentaction.ui.ui_error.UiError
import com.since.presentaction.ui.ui_error.asUiError
import com.since.run.domain.overview_screen.OverviewRepo
import com.since.run.presentaction.over_view.event.OverViewEvent
import com.since.run.presentaction.over_view.state_action.OverViewState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class OverViewViewModel(
    private val overViewRepo: OverviewRepo
): ViewModel() {

    var state by mutableStateOf(OverViewState())
        private set


    private val _channel = Channel<OverViewEvent>()
    val channel = _channel.receiveAsFlow()

    init {
        getSingleChar()
    }

    private fun getSingleChar(){
        viewModelScope.launch {
            state=state.copy(isLoading = true)
            val result = overViewRepo.getFirstChar()
            state=state.copy(isLoading = false)

            when(result){
                is Result.Error<DataError.NetworkError> -> {
                    _channel.send(OverViewEvent.Error(error = result.error.asUiError()))
                }
                is Result.Success -> {
                    state=state.copy(
                        singleChar = result.success.name
                    )
                    _channel.send(OverViewEvent.Success)
                }
            }

        }
    }

}