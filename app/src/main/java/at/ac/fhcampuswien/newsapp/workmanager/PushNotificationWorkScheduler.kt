package at.ac.fhcampuswien.newsapp.workmanager

import android.app.Application
import androidx.work.*


import java.util.concurrent.TimeUnit


class PushNotificationWorkScheduler {
    private val notificationWorkTag = "notification_work"

    fun scheduleNotificationWork(application: Application) {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
            .setRequiresBatteryNotLow(true)
            .build()

        val notificationWorkRequest = PeriodicWorkRequestBuilder<PushNotificationWorker>(
            24, TimeUnit.HOURS
        )
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(application).enqueueUniquePeriodicWork(
            notificationWorkTag,
            ExistingPeriodicWorkPolicy.KEEP,
            notificationWorkRequest
        )
    }

}