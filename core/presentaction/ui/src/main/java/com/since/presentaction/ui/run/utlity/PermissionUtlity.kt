package com.since.presentaction.ui.run.utlity

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.core.app.ComponentActivity

fun ComponentActivity.shouldShowNotificationRational(): Boolean{
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)
    } else {
        true
    }
}

fun ComponentActivity.shouldShowLocationRational(): Boolean{
    return shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)
}


private fun Context.checkPermission(name:String): Boolean{
    return checkSelfPermission(name) == PackageManager.PERMISSION_GRANTED
}


fun Context.notificationPermission(): Boolean{
    return if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.TIRAMISU){
        checkPermission(Manifest.permission.POST_NOTIFICATIONS)
    }else{
        true
    }
}


fun Context.fineLocationPermission(): Boolean{
    return checkPermission(Manifest.permission.ACCESS_FINE_LOCATION)
}


fun Context.coarseLocationPermission(): Boolean{
    return checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
}


fun Context.goToAppSetting(): Intent {
    return Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package",packageName,null)).also(::startActivity)
}