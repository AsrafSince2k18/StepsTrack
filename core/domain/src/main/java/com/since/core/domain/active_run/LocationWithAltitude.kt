package com.since.core.domain.active_run


//todo purpose android come from location lat,lng,alt this data class help mapper class
data class LocationWithAltitude(
    val location: Location,
    val altitude: Double
)
