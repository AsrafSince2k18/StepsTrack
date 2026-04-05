package com.since.run.location

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Looper
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.since.core.domain.active_run.LocationWithAltitude
import com.since.run.domain.repo.LocationObserve
import com.since.run.location.mapper.toLocationWithAltitude
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class LocationObserveImpl(
    private val context: Context
) : LocationObserve{

    private val client = LocationServices.getFusedLocationProviderClient(context)

    override fun observeLocation(interval:Long): Flow<LocationWithAltitude> {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return callbackFlow {

            var isNetworkProvide=false
            var isGpsProvide=false
            while (!isNetworkProvide || !isGpsProvide) {
                isNetworkProvide =
                    locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
                isGpsProvide = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                delay(1000)
            }


            if(ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                close()
                return@callbackFlow
            }else {
                client.lastLocation.addOnSuccessListener { location ->
                    trySend(location.toLocationWithAltitude())
                }

                val locationRequest =
                    LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, interval)
                        .build()

                val locationCallBack = object : LocationCallback() {
                    override fun onLocationResult(result: LocationResult) {
                        super.onLocationResult(result)
                        result.locations.lastOrNull()?.let {
                            trySend(it.toLocationWithAltitude())
                        }
                    }
                }

                client.requestLocationUpdates(
                    locationRequest,
                    locationCallBack,
                    Looper.getMainLooper()
                )


                awaitClose {
                    client.removeLocationUpdates(locationCallBack)
                }
            }

        }

    }


}