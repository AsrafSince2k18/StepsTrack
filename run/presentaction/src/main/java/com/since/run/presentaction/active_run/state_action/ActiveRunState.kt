package com.since.run.presentaction.active_run.state_action

import com.since.core.domain.active_run.Location
import com.since.run.domain.activerun_screen.RunData
import kotlin.time.Duration

data class ActiveRunState(

    val elapsedTime: Duration= Duration.ZERO,

    val currentTime:Long = System.currentTimeMillis(),

    val allPermissionEnableOrNot : Boolean=false,

    val activeRunOrNot : Boolean=false,

    val stopRun: Boolean?=null,

    val location: Location ?=null,

    val runData : RunData = RunData(),


    //todo permissionVariable
    val notificationRational : Boolean=false,
    val locationRational: Boolean=false,

    val notificationPermission: Boolean=false,
    val notificationPermissionValid: Boolean=false,
    val locationPermission: Boolean=false,
    val locationPermissionValid: Boolean=false,

    //todo networkStatus
    val networkStatus: Boolean?=false


)
