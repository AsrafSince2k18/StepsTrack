package com.since.run.presentaction.active_run.component.map.polyline

import androidx.compose.ui.graphics.Color
import com.since.core.domain.active_run.Location

data class PolylineModel(

    val staringPoint : Location,
    val endingPoint : Location,
    val color: Color

)
