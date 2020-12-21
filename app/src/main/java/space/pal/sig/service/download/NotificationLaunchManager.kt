package space.pal.sig.service.download

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

/**
 * SpaceNow
 * Created by Catalin on 7/31/2020
 */
class NotificationLaunchManager(
        context: Context,
        workerParams: WorkerParameters
) : Worker(context, workerParams) {

    override fun doWork(): Result {
        return Result.success()
    }

    companion object {
        const val NOTIFICATION_LAUNCH_WORKER_TAG = "notification_launch_worker_tag"
    }
}