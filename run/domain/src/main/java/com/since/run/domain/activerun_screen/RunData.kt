package com.since.run.domain.activerun_screen

import com.since.core.domain.active_run.LocationWithTimeStamp
import kotlin.time.Duration

data class RunData(

    val distanceMeter:Int=0,
    val pace: Duration= Duration.ZERO,
    val listOfListLocation : List<List<LocationWithTimeStamp>> = emptyList()

)
