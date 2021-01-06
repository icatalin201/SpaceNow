package space.pal.sig.service

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.work.*
import java.util.concurrent.TimeUnit

/**
 * SpaceNow
 * Created by Catalin on 7/25/2020
 */
object ScheduleManager {

    @JvmStatic
    fun scheduleDataSync(context: Context, repeatInterval: Long) {
        createQueue(context, DataSyncWorker::class.java, repeatInterval,
                DataSyncWorker.DATA_SYNC_WORKER_TAG)
    }

    @JvmStatic
    fun scheduleLaunchNotification(context: Context, repeatInterval: Long) {
        createQueue(context, NotificationLaunchManager::class.java, repeatInterval,
                NotificationLaunchManager.NOTIFICATION_LAUNCH_WORKER_TAG)
    }

    @JvmStatic
    fun launchOneTimeSync(context: Context, tag: String): LiveData<List<WorkInfo>> {
        val manager = WorkManager.getInstance(context)
        manager.cancelAllWorkByTag(tag)
        manager.pruneWork()
        val workRequest = createOneTimeWorkRequest(DataSyncWorker::class.java, tag)
        manager.enqueue(workRequest)
        return manager.getWorkInfosByTagLiveData(tag)
    }

    private fun createQueue(context: Context,
                            workerClass: Class<out ListenableWorker?>,
                            repeatInterval: Long,
                            tag: String) {
        val manager = WorkManager.getInstance(context)
        manager.cancelAllWorkByTag(tag)
        val workRequest = createPeriodicWorkRequest(workerClass, repeatInterval, tag)
        manager.enqueue(workRequest)
    }

    private fun createPeriodicWorkRequest(
            workerClass: Class<out ListenableWorker?>,
            repeatInterval: Long,
            tag: String): PeriodicWorkRequest {
        return PeriodicWorkRequest.Builder(workerClass, repeatInterval, TimeUnit.MINUTES)
                .setConstraints(constraints)
                .addTag(tag)
                .build()
    }

    private fun createOneTimeWorkRequest(
            workerClass: Class<out ListenableWorker?>,
            tag: String): OneTimeWorkRequest {
        return OneTimeWorkRequest.Builder(workerClass)
                .setConstraints(constraints)
                .addTag(tag)
                .build()
    }

    private val constraints: Constraints
        get() = Constraints.Builder()
                .setRequiresBatteryNotLow(true)
                .setRequiredNetworkType(NetworkType.UNMETERED)
                .build()
}