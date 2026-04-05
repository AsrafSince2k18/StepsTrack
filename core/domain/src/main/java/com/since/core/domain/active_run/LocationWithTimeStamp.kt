package com.since.core.domain.active_run

import kotlin.time.Duration


//todo this data class help of calculate speed,distance,polylineColor
data class LocationWithTimeStamp(

    val locationWithAltitude: LocationWithAltitude,
    val elapsedTime: Duration

)
