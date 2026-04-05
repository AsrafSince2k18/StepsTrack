package com.since.run.presentaction.active_run.state_action

sealed interface ActiveRunAction {

    data class NotificationInfo(
        val permission: Boolean,
        val rational: Boolean
    ): ActiveRunAction

    data class LocationInfo(
        val permission: Boolean,
        val rational: Boolean
    ): ActiveRunAction

    data object ClearPermissionDialog : ActiveRunAction

    data object GoBackHomeScreen: ActiveRunAction

    data object StartRun: ActiveRunAction

    data object StopRun: ActiveRunAction


}