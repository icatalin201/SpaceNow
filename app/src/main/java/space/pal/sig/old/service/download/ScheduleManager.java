package space.pal.sig.old.service.download;

import android.content.Context;

import androidx.work.Constraints;
import androidx.work.ListenableWorker;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import java.util.concurrent.TimeUnit;

/**
 * SpaceNow
 * Created by Catalin on 7/25/2020
 **/
public class ScheduleManager {

    public static void scheduleDataSync(Context context, long repeatInterval, String tag) {
        createQueue(context, DataSyncManager.class, repeatInterval, tag);
    }

    public static void scheduleLaunchNotification(Context context, long repeatInterval, String tag) {
        createQueue(context, NotificationLaunchManager.class, repeatInterval, tag);
    }

    public static void launchOneTimeSync(Context context, String tag) {
        WorkManager manager = WorkManager.getInstance(context);
        manager.cancelAllWorkByTag(tag);
        OneTimeWorkRequest workRequest = createOneTimeWorkRequest(DataSyncManager.class, tag);
        manager.enqueue(workRequest);
    }

    private static void createQueue(Context context,
                                    Class<? extends ListenableWorker> workerClass,
                                    long repeatInterval,
                                    String tag) {
        WorkManager manager = WorkManager.getInstance(context);
        manager.cancelAllWorkByTag(tag);
        PeriodicWorkRequest workRequest = createPeriodicWorkRequest(workerClass, repeatInterval, tag);
        manager.enqueue(workRequest);
    }

    private static PeriodicWorkRequest createPeriodicWorkRequest(
            Class<? extends ListenableWorker> workerClass,
            long repeatInterval,
            String tag) {
        return new PeriodicWorkRequest.Builder(workerClass, repeatInterval, TimeUnit.MINUTES)
                .setConstraints(getConstraints())
                .addTag(tag)
                .build();
    }

    private static OneTimeWorkRequest createOneTimeWorkRequest(
            Class<? extends ListenableWorker> workerClass,
            String tag) {
        return new OneTimeWorkRequest.Builder(workerClass)
                .setConstraints(getConstraints())
                .addTag(tag)
                .build();
    }

    private static Constraints getConstraints() {
        return new Constraints.Builder()
                .setRequiresBatteryNotLow(true)
                .setRequiredNetworkType(NetworkType.UNMETERED)
                .build();
    }

}
