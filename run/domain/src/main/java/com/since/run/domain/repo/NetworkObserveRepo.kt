package com.since.run.domain.repo

import kotlinx.coroutines.flow.Flow

interface NetworkObserveRepo {

    fun observeNetwork(): Flow<Boolean>

}