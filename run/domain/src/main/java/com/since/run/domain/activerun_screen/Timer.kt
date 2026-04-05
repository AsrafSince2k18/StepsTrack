package com.since.run.domain.activerun_screen

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

object Timer {


    fun elapsedTimer(): Flow<Duration>{
        return flow {
            var lastTime = System.currentTimeMillis()
            while(true){
                val currentTime = System.currentTimeMillis()
                val elapsedTime = currentTime-lastTime
                emit(elapsedTime.milliseconds)
                delay(200L)
                lastTime=currentTime
            }
        }
    }

}