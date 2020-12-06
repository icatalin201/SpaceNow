package space.pal.sig.old.view.splash;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import space.pal.sig.old.util.SharedPreferencesUtil;

import static space.pal.sig.old.service.download.DataSyncManager.DATA_SYNC_WORKER_TAG;
import static space.pal.sig.old.service.download.NotificationLaunchManager.NOTIFICATION_LAUNCH_WORKER_TAG;
import static space.pal.sig.old.service.download.ScheduleManager.launchOneTimeSync;
import static space.pal.sig.old.service.download.ScheduleManager.scheduleDataSync;
import static space.pal.sig.old.service.download.ScheduleManager.scheduleLaunchNotification;

/**
 * SpaceNow
 * Created by Catalin on 7/22/2020
 **/
public class SplashViewModel extends AndroidViewModel {

    private static final String IS_FIRST_TIME = "Is.First.Time";
    private static final String TAG_OUTPUT = "Worker.Tag.Output";
    private final WorkManager manager;
    private final SharedPreferencesUtil sharedPreferencesUtil;
    private final MediatorLiveData<Boolean> safeForStart = new MediatorLiveData<>();

    public SplashViewModel(@NonNull Application application,
                           SharedPreferencesUtil sharedPreferencesUtil) {
        super(application);
        this.manager = WorkManager.getInstance(application);
        this.sharedPreferencesUtil = sharedPreferencesUtil;
        boolean isFirstTime = isFirstTime();
        if (isFirstTime) {
            setupWork();
        } else {
            launchOneTimeSync(getApplication(), "sync");
        }
        this.safeForStart.setValue(!isFirstTime);
    }

    public LiveData<Boolean> isSafeForStart() {
        return safeForStart;
    }

    private void setupWork() {
        safeForStart.addSource(manager.getWorkInfosByTagLiveData(TAG_OUTPUT),
                workInfoList -> {
                    WorkInfo workInfo = workInfoList.get(0);
                    boolean finished = workInfo.getState().isFinished();
                    if (finished) {
                        sharedPreferencesUtil.storeBoolean(IS_FIRST_TIME, false);
                        setupScheduling();
                    }
                    safeForStart.setValue(finished);
                });
        OneTimeWorkRequest worker = new OneTimeWorkRequest
                .Builder(DataLoadWorker.class)
                .addTag(TAG_OUTPUT)
                .build();
        manager.enqueue(worker);
    }

    private void setupScheduling() {
        String dataSyncRepeatInterval = sharedPreferencesUtil
                .getString(NOTIFICATION_LAUNCH_WORKER_TAG, "180");
        scheduleLaunchNotification(getApplication(), 15, NOTIFICATION_LAUNCH_WORKER_TAG);
        scheduleDataSync(getApplication(), Long.parseLong(dataSyncRepeatInterval), DATA_SYNC_WORKER_TAG);
    }

    private boolean isFirstTime() {
        return sharedPreferencesUtil.getBoolean(IS_FIRST_TIME, true);
    }
}
