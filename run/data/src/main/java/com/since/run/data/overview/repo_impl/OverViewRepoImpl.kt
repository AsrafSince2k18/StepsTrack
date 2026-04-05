package com.since.run.data.overview.repo_impl

import com.since.core.data.network.utility.get
import com.since.core.domain.error.DataError
import com.since.core.domain.error.Result
import com.since.core.domain.error.map
import com.since.run.data.overview.mapper.toOverViewModel
import com.since.run.data.overview.model.OverViewModelSerialzable
import com.since.run.domain.overview_screen.OverViewModel
import com.since.run.domain.overview_screen.OverviewRepo
import io.ktor.client.HttpClient

class OverViewRepoImpl(
    private val client: HttpClient
): OverviewRepo {
    override suspend fun getFirstChar(): Result<OverViewModel, DataError.NetworkError> {
        return client.get<OverViewModelSerialzable>(url = "/user/name").map { it.toOverViewModel() }
    }
}