package com.since.presentaction.ui.run.utlity

import java.text.SimpleDateFormat
import java.util.Date
import kotlin.math.pow
import kotlin.math.round
import kotlin.time.Duration



fun Long.timeFormat():String{

    val sdf = SimpleDateFormat("hh:mm")
    val date = Date(this)
    return sdf.format(date)
}

fun Duration.toTimeFormat():String{

    val hours = this.inWholeSeconds/3600
    val minutes = (inWholeSeconds%36000) / 60
    val second = inWholeSeconds%60

    return "%02d:%02d:%02d".format(hours,minutes,second)
}



private fun Double.decimalCount(count: Int): Double{
    val factor = 10f.pow(count)
    return round(this*factor)/factor
}


fun Double.formatedKm():String{
    return "${decimalCount(1)} km"
}

fun Duration.paceCalculate(distanceKm:Double):String{

    if(this == Duration.ZERO || distanceKm < 0.0){
        return "-"
    }

    val averagePaceKM = (inWholeSeconds/distanceKm).toInt()

    val minutes = averagePaceKM/60

    val second = averagePaceKM%60
    return "%02d:%02d".format(minutes,second)
}