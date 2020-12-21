package space.pal.sig.service.download

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

/**
 * SpaceNow
 * Created by Catalin on 7/31/2020
 */
class DataSyncManager(
        context: Context,
        workerParams: WorkerParameters
) : Worker(context, workerParams) {

    override fun doWork(): Result {
        return Result.success()
    }

    companion object {
        const val DATA_SYNC_WORKER_TAG = "data_sync_worker_tag"
    }
}