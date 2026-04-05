package com.since.run.domain.overview_screen

import com.since.core.domain.error.DataError
import com.since.core.domain.error.Result

interface OverviewRepo {

    suspend fun getFirstChar() : Result<OverViewModel, DataError.NetworkError>

}