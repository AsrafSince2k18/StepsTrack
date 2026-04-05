package com.since.run.domain.repo

import com.since.core.domain.active_run.LocationWithAltitude
import kotlinx.coroutines.flow.Flow

interface LocationObserve {

    fun observeLocation(interval:Long) : Flow<LocationWithAltitude>

}