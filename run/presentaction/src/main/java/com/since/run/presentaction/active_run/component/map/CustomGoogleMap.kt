package com.since.run.presentaction.active_run.component.map

import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.MarkerComposable
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import com.since.core.domain.active_run.Location
import com.since.core.domain.active_run.LocationWithTimeStamp
import com.since.run.presentaction.R
import com.since.run.presentaction.active_run.component.map.polyline.PolylineUi
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin


private fun calculateBearing(start: LatLng, end: LatLng): Float {
    val lat1 = Math.toRadians(start.latitude)
    val lon1 = Math.toRadians(start.longitude)
    val lat2 = Math.toRadians(end.latitude)
    val lon2 = Math.toRadians(end.longitude)
    val dLon = lon2 - lon1
    val y = sin(dLon) * cos(lat2)
    val x = cos(lat1) * sin(lat2) - sin(lat1) * cos(lat2) * cos(dLon)
    val brng = Math.toDegrees(atan2(y, x))
    return (((brng + 360.0) % 360.0).toFloat())
}



private fun shortestAngle(from: Float, to: Float): Float {
    val normalizedFrom = (from % 360 + 360) % 360
    val normalizedTo = (to % 360 + 360) % 360
    val delta = ((normalizedTo - normalizedFrom + 540f) % 360f) - 180f
    return normalizedFrom + delta
}


@Composable
fun MarkerUi(
    bearing: Float,
    markerState: MarkerState,
    location: Location
) {

    val rotationAnim = remember { Animatable((bearing + 360f) % 360f) }

    LaunchedEffect(location) {
        val current = rotationAnim.value
        val target = (bearing + 360f) % 360f
        val nearestTarget = shortestAngle(current, target)
        rotationAnim.animateTo(
            nearestTarget,
            animationSpec = tween(durationMillis = 350)
        )
    }


    MarkerComposable(
        location,
        state = markerState
    ) {

        Column(
            modifier=Modifier
                .width(50.dp)
                .rotate(bearing),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Icon(
                imageVector = Icons.Rounded.KeyboardArrowUp,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )


            Box(
                modifier=Modifier
                    .size(20.dp)
                    .clip(CircleShape)
                    .background(
                        MaterialTheme.colorScheme.primary.copy(0.2f),
                        shape = CircleShape
                    )
                    .rotate(bearing),
                contentAlignment = Alignment.Center
            ){
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .clip(CircleShape)
                        .background(
                            MaterialTheme.colorScheme.primary,
                            shape = CircleShape
                        )
                )
            }
        }
    }
}

@Composable
fun CustomGoogleMap(
    modifier: Modifier = Modifier,
    location: Location?,
    listOfListLocation: List<List<LocationWithTimeStamp>>,
    runningStatus: Boolean
    ) {

    val context = LocalContext.current
    val mapStyle = MapStyleOptions.loadRawResourceStyle(context,R.raw.custommapstyle)

    val cameraPositionState  = rememberCameraPositionState()

    val animateLat by animateFloatAsState(
        targetValue = location?.lat?.toFloat() ?: 0f,
        animationSpec = tween(durationMillis = 500)
    )

    val animateLng by animateFloatAsState(
        targetValue = location?.lng?.toFloat() ?: 0f,
        animationSpec = tween(durationMillis = 500)
    )

    val latLng = remember(location) {
        LatLng(
            animateLat.toDouble(),
            animateLng.toDouble()
        )

    }
    val markerState = rememberMarkerState(position = latLng)


    var perivosLatLng by remember { mutableStateOf<LatLng?>(null) }
    var bearing by remember { mutableFloatStateOf(0f) }


    LaunchedEffect(location) {
        location?.let {
            val newLatLng = LatLng(
                it.lat,
                it.lng
            )

            perivosLatLng?.let { pervios->
                bearing = calculateBearing(pervios, newLatLng)
            }
            perivosLatLng=newLatLng
        }
    }


    //todo activeRunScreen immedetly current location show help of this sideEffect
    LaunchedEffect(runningStatus,location) {
        if(!runningStatus || location!=null){
            Log.e("e1", "launchedEffect 1: $runningStatus", )
            markerState.position=latLng
        }
    }


    LaunchedEffect(runningStatus,location) {
        if(runningStatus || location!=null){

            val latLng = LatLng(
                location?.lat ?: 0.0,
                location?.lng ?: 0.0
            )
            cameraPositionState.animate(
                CameraUpdateFactory.newLatLngZoom(latLng,18f)
            )

        }
    }


    GoogleMap(
        modifier = modifier,
        cameraPositionState = cameraPositionState,
        uiSettings = MapUiSettings(
            zoomControlsEnabled = false
        ),
        properties = MapProperties(
            mapStyleOptions = mapStyle
        )
    ){

        PolylineUi(
            locationListOfListLocation = listOfListLocation
        )

        if(location!=null) {
           MarkerUi(
                bearing = bearing,
                markerState = markerState,
                location = location
            )
        }

    }



    
}