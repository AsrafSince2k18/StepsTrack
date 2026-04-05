package com.since.run.domain.activerun_screen

import com.since.core.domain.active_run.LocationWithTimeStamp

fun locationToLocationDistance(
    location: List<List<LocationWithTimeStamp>>
):Int{
    //todo each inner list location calucation
    val distance = location
        .sumOf {
            it.zipWithNext { l1,l2->
                l1.locationWithAltitude.location.distanceTo(l2.locationWithAltitude.location)
            }.sum().toInt()
        }

    return distance
}