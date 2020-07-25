package space.pal.sig.view.splash;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import space.pal.sig.util.SharedPreferencesUtil;

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
                    }
                    safeForStart.setValue(finished);
                });
        OneTimeWorkRequest worker = new OneTimeWorkRequest
                .Builder(DataLoadWorker.class)
                .addTag(TAG_OUTPUT)
                .build();
        manager.enqueue(worker);
    }

    private boolean isFirstTime() {
        return sharedPreferencesUtil.getBoolean(IS_FIRST_TIME, true);
    }
}
