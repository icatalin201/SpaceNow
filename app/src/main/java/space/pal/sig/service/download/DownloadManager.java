package space.pal.sig.service.download;

import android.content.Context;

import androidx.work.Constraints;
import androidx.work.ListenableWorker;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

/**
 * SpaceNow
 * Created by Catalin on 7/25/2020
 **/
public class DownloadManager {

    public static void createSyncQueue(Context context) {
        WorkManager manager = WorkManager.getInstance(context);
        manager.beginWith(createWorkRequest(ApodSyncManager.class))
                .then(createWorkRequest(LaunchesSyncManager.class))
                .then(createWorkRequest(EsaNewsSyncManager.class))
                .then(createWorkRequest(HubbleNewsSyncManager.class))
                .then(createWorkRequest(JwNewsSyncManager.class))
                .then(createWorkRequest(StNewsSyncManager.class))
                .enqueue();
    }

    private static OneTimeWorkRequest createWorkRequest(Class<? extends ListenableWorker> workerClass) {
        Constraints constraints = new Constraints.Builder()
                .setRequiresBatteryNotLow(true)
                .setRequiredNetworkType(NetworkType.UNMETERED)
                .build();
        return new OneTimeWorkRequest.Builder(workerClass)
                .setConstraints(constraints)
                .build();
    }

}
