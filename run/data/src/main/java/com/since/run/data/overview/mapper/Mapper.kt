package com.since.run.data.overview.mapper

import com.since.run.data.overview.model.OverViewModelSerialzable
import com.since.run.domain.overview_screen.OverViewModel

fun OverViewModelSerialzable.toOverViewModel(): OverViewModel{
    return OverViewModel(name=name)
}