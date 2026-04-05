package com.since.run.presentaction.active_run.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.since.run.domain.activerun_screen.RunData
import com.since.run.domain.activerun_screen.TrackerData
import com.since.run.domain.repo.NetworkObserveRepo
import com.since.run.presentaction.active_run.service.TimerForegroundService.Companion.SERVICE_STATUS
import com.since.run.presentaction.active_run.state_action.ActiveRunAction
import com.since.run.presentaction.active_run.state_action.ActiveRunState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.time.Duration

class ActiveRunViewModel(
    private val networkObserveRepo: NetworkObserveRepo,
    private val trackerData: TrackerData
): ViewModel() {

    private val TAG="t1"
    var state by mutableStateOf(ActiveRunState(
        activeRunOrNot = SERVICE_STATUS
    ))
        private set


    private val observeNetworkStatus = networkObserveRepo.observeNetwork()
        .stateIn(viewModelScope, SharingStarted.Lazily,null)


    private val notificationFlow = snapshotFlow { state.notificationPermission }
    private val locationFlow = snapshotFlow { state.locationPermission }

    private val networkFlow = snapshotFlow { state.networkStatus }

    private val startRunOrNot  = snapshotFlow { state.activeRunOrNot }
    private val allPermissionFlowAndInternet = combine(notificationFlow,locationFlow,networkFlow){ notification, location,networkStatus->
        notification&&location&&networkStatus!!
    }




    init {
        trackerData.time
            .onEach {
                state=state.copy(
                    currentTime = it
                )
            }.launchIn(viewModelScope)

        observeNetworkStatus
            .filterNotNull()
            .onEach { status->
                state=state.copy(
                    networkStatus = status
                )
            }.launchIn(viewModelScope)

        allPermissionFlowAndInternet
            .onEach { grented->
                trackerData.startObserveLocation(grented)
            }.launchIn(viewModelScope)


        //TODO when start startObserving immeditly get current location
        trackerData.currentLocation
            .onEach {
                state=state.copy(
                    location = it?.location
                )
            }.launchIn(viewModelScope)



        //TODO startRun
        startRunOrNot.onEach {runStatus->
            trackerData.runStatus(runStatus)
        }.launchIn(viewModelScope)


        //TODO duration and runData
        combine(trackerData.runData,trackerData.duration){runData,duration->
            state=state.copy(
                runData = runData,
                elapsedTime = duration
            )
        }.launchIn(viewModelScope)


    }


    fun onAction(action: ActiveRunAction){
        when(action){
            is ActiveRunAction.LocationInfo -> {
                state=state.copy(
                    locationRational = action.rational,
                    locationPermission = action.permission,
                    locationPermissionValid = !action.rational && !action.permission
                )
            }
            is ActiveRunAction.NotificationInfo -> {
                state=state.copy(
                    notificationRational = action.rational,
                    notificationPermission = action.permission,
                    notificationPermissionValid = !action.rational && !action.permission
                )
            }
            ActiveRunAction.ClearPermissionDialog -> {
                if(state.locationPermission && state.notificationPermission){
                    state=state.copy(
                        locationPermissionValid = false,
                        notificationPermissionValid = false
                    )
                }
            }
            ActiveRunAction.StartRun -> {
                state=state.copy(
                    activeRunOrNot = !state.activeRunOrNot,
                    stopRun = if(state.activeRunOrNot) false else null
                )
                Log.i(TAG, "onAction: ${state.stopRun}")
            }
            ActiveRunAction.GoBackHomeScreen -> {
                state=state.copy(
                    activeRunOrNot = false
                )
            }

            ActiveRunAction.StopRun -> {
                //TODO service stop
                SERVICE_STATUS=false
                trackerData.stopRunning()
                viewModelScope.launch {
                    delay(500L)
                    state=state.copy(stopRun = null)
                }
            }
        }
    }

}