package com.since.run.presentaction.active_run.component.map.polyline

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.google.android.gms.maps.model.JointType
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.Polygon
import com.since.core.domain.active_run.LocationWithTimeStamp

@Composable
fun PolylineUi(
    locationListOfListLocation : List<List<LocationWithTimeStamp>>
) {


    val location = remember(locationListOfListLocation) {
        locationListOfListLocation.map {
            it.zipWithNext {startingPoint,endingPoint->
                PolylineModel(
                    staringPoint = startingPoint.locationWithAltitude.location,
                    endingPoint = endingPoint.locationWithAltitude.location,
                    color = PolylineCalculator.colorLine(
                        location1 = startingPoint,
                        location2 = endingPoint
                    )
                )
            }
        }
    }



    location.forEach {
        it.forEach { poly->
            Polygon(
                points = listOf(
                    LatLng(poly.staringPoint.lat,poly.staringPoint.lng),
                    LatLng(poly.endingPoint.lat,poly.endingPoint.lng),
                ),
                strokeColor = poly.color,
                fillColor = poly.color,
                strokeJointType = JointType.BEVEL
            )
        }
    }



}