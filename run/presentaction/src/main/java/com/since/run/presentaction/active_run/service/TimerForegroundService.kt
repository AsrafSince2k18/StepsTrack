package com.since.run.presentaction.active_run.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.app.TaskStackBuilder
import androidx.core.net.toUri
import com.since.presentaction.designsystem.R
import com.since.presentaction.ui.run.utlity.toTimeFormat
import com.since.run.domain.activerun_screen.TrackerData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.android.ext.android.inject

class TimerForegroundService : Service() {


    private val trackerData by inject<TrackerData>()
    private var scope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    private val notificationManger by lazy {
        getSystemService(NOTIFICATION_SERVICE) as NotificationManager
    }

    companion object {
        private const val CHANNEL_ID = "Timer"
        private const val CHANNEL_NAME = "TIMER"

        private const val ACTION_START = "START"

        private const val ACTION_STOP = "STOP"

         var SERVICE_STATUS = false

        private const val KEY = "key"


        fun startService(context: Context, activity: Class<*>): Intent {
            val intent = Intent(context, TimerForegroundService::class.java).apply {
                action = ACTION_START
                putExtra(KEY, activity.name)
            }
            return intent
        }

        fun stopService(context: Context): Intent {
            return Intent(context, TimerForegroundService::class.java).apply {
                action = ACTION_STOP
            }
        }

    }

    private fun notificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManger.createNotificationChannel(channel)
        }
    }

    private val baseNotification = {
        NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(R.drawable.app_logo)
            .setContentTitle("Elapsed Time")
            .setOnlyAlertOnce(true)
    }


    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        when (intent?.action) {
            ACTION_START -> {
                val className = intent.getStringExtra(KEY)
                    ?: throw IllegalArgumentException("Not found activity")

                start(activity = Class.forName(className))

            }

            ACTION_STOP -> {
                stop()
            }
        }

        return START_STICKY

    }


    private fun start(activity: Class<*>) {
        if (!SERVICE_STATUS) {
            SERVICE_STATUS = true
        }
        notificationChannel()

        val intent = Intent(applicationContext, activity).apply {
            data = "steps://tracker".toUri()
            addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        }

        val pendingIntent = TaskStackBuilder.create(applicationContext).run {
            addNextIntentWithParentStack(intent)
            getPendingIntent(0, PendingIntent.FLAG_IMMUTABLE)
        }


        val notification = baseNotification()
            .setContentText("00:00:00")
            .setContentIntent(pendingIntent)
            .build()
        notificationManger.notify(1, notification)
        startForeground(1, notification)

        trackerData.duration
            .onEach {
                val updateNotification = baseNotification()
                    .setContentText(it.toTimeFormat())
                    .setContentIntent(pendingIntent)
                    .build()
                notificationManger.notify(1, updateNotification)
            }.launchIn(scope)
    }

    private fun stop() {
        if (SERVICE_STATUS) {
            SERVICE_STATUS = false
        }
        stopSelf()
        notificationManger.cancel(1)
        scope.cancel()
        scope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    }

}
