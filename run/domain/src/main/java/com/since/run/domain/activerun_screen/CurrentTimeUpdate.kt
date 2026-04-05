package com.since.run.domain.activerun_screen

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

object CurrentTimeUpdate {

    fun currentTime(): Flow<Long>{
        return flow{
            while (true){
               val currentTime = System.currentTimeMillis()
               emit(currentTime)
                delay(1000)
            }
        }
    }

}