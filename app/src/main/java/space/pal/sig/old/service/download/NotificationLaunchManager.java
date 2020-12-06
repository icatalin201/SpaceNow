package space.pal.sig.old.service.download;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.util.Set;

import space.pal.sig.old.Space;
import space.pal.sig.old.injection.ApplicationComponent;
import space.pal.sig.old.model.Launch;
import space.pal.sig.old.repository.LaunchesRepository;
import space.pal.sig.old.util.SharedPreferencesUtil;

import static space.pal.sig.old.service.notification.SpaceNotificationManager.createNotification;

/**
 * SpaceNow
 * Created by Catalin on 7/31/2020
 **/
public class NotificationLaunchManager extends Worker {

    public static final String NOTIFICATION_LAUNCH_WORKER_TAG = "notification_launch_worker_tag";

    public NotificationLaunchManager(@NonNull Context context,
                                     @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        ApplicationComponent applicationComponent = Space.getApplicationComponent();
        SharedPreferencesUtil sharedPreferencesUtil = applicationComponent.sharedPreferencesUtil();
        LaunchesRepository launchesRepository = applicationComponent.launchesRepository();
        Set<String> launchesNotification = sharedPreferencesUtil.getSet("notifications");
        long period = Long.parseLong(sharedPreferencesUtil
                .getString(NOTIFICATION_LAUNCH_WORKER_TAG, "15"));
        for (String launchId : launchesNotification) {
            Launch launch = launchesRepository.findLaunchById(Long.parseLong(launchId));
            if (launch.getTimestamp() < System.currentTimeMillis()) {
                continue;
            }
            if (launch.getTimestamp() <= System.currentTimeMillis() + period) {
                createNotification(getApplicationContext(),
                        String.format("%s is about to launch!", launch.getName()));
                launchesNotification.remove(launchId);
                sharedPreferencesUtil.storeSet("notifications", launchesNotification);
            }
        }
        return Result.success();
    }
}
