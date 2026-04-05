package com.since.run.location.mapper

import android.location.Location
import com.since.core.domain.active_run.LocationWithAltitude

fun Location.toLocationWithAltitude(): LocationWithAltitude{
    return LocationWithAltitude(
        location = com.since.core.domain.active_run.Location(
            lat = latitude,
            lng = longitude
        ),
        altitude = altitude
    )
}