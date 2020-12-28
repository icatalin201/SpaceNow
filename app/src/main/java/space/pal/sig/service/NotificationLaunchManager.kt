package space.pal.sig.service

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import space.pal.sig.R
import space.pal.sig.repository.LaunchRepository
import space.pal.sig.service.SpaceNotificationManager.createNotification

/**
 * SpaceNow
 * Created by Catalin on 7/31/2020
 */
class NotificationLaunchManager(
        context: Context,
        workerParams: WorkerParameters
) : Worker(context, workerParams), KoinComponent {

    companion object {
        private const val NOTIFICATION_PERIOD: Long = 900000
        const val NOTIFICATION_LAUNCH_WORKER_TAG = "notification_launch_worker_tag"
    }

    private val launchRepository: LaunchRepository by inject()

    override fun doWork(): Result {
        val launchesNotification = launchRepository.getLaunchNotifications()
        for (launchId in launchesNotification) {
            val launch = launchRepository.getById(launchId)
            if (launch.dateUnix < System.currentTimeMillis()) {
                continue
            }
            if (launch.dateUnix <= System.currentTimeMillis() + NOTIFICATION_PERIOD) {
                createNotification(applicationContext,
                        applicationContext.getString(R.string.launch_notification_message))
                launchesNotification.remove(launchId)
                launchRepository.saveLaunchNotifications(launchesNotification)
            }
        }
        return Result.success()
    }
}