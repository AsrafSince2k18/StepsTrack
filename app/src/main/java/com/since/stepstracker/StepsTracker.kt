package com.since.stepstracker

import android.app.Application
import com.since.auth.data.di.authDataModule
import com.since.core.data.di.coreDataModule
import com.since.presentaction.di.authPresentactionModule
import com.since.run.data.di.runDataModule
import com.since.run.location.di.runLocationModule
import com.since.run.presentaction.di.runPresentationModule
import com.since.stepstracker.di.appModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

class StepsTracker : Application() {


    val application = CoroutineScope(Dispatchers.IO+ SupervisorJob())

    override fun onCreate() {
        super.onCreate()

        if(BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
        }


        startKoin {
            androidContext(this@StepsTracker)
            androidLogger(level = Level.DEBUG)
            modules(
                listOf(
                    appModule,
                    authDataModule,
                    authPresentactionModule,
                    coreDataModule,
                    runDataModule,
                    runLocationModule,
                    runPresentationModule
                )
            )
        }

    }

}