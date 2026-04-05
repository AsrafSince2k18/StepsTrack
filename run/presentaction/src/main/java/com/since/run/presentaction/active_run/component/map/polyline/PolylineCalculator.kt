package com.since.run.presentaction.active_run.component.map.polyline

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.graphics.ColorUtils
import com.since.core.domain.active_run.LocationWithTimeStamp
import kotlin.math.abs

object PolylineCalculator {



    fun colorLine(
        location1: LocationWithTimeStamp,
        location2 : LocationWithTimeStamp
    ): Color{

        val distance = location1.locationWithAltitude.location.distanceTo(
            location2.locationWithAltitude.location)


        val timeDiff = abs((location2.elapsedTime - location1.elapsedTime).inWholeSeconds)
        val speedKm = (distance/timeDiff)*3.6

        return polylineColor(
            speedKm = speedKm,
            minSpeed = 5.0,
            maxSpeed = 25.0,
            colorStart = Color.Green,
            colorMid = Color.Yellow,
            colorEnd = Color.Red
        )


    }




    private fun polylineColor(
        speedKm: Double,
        minSpeed: Double,
        maxSpeed: Double,
        colorStart: Color,
        colorMid: Color,
        colorEnd: Color
    ): Color{

        val ration = ((speedKm-minSpeed)/(maxSpeed-minSpeed)).coerceIn(0.0..1.0)

        val colorRation = if(ration<=0.5){
            val startToMidColor = ration/0.5
            ColorUtils.blendARGB(colorStart.toArgb(),colorMid.toArgb(),startToMidColor.toFloat())
        }else{
            val midToEndColor = (ration-0.5)/0.5
            ColorUtils.blendARGB(colorMid.toArgb(),colorEnd.toArgb(),midToEndColor.toFloat())

        }
        return Color(colorRation)
    }


}