package space.pal.sig;

import android.app.Application;
import android.content.IntentFilter;

import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.WorkManager;

import space.pal.sig.injection.ApiServiceModule;
import space.pal.sig.injection.ApplicationComponent;
import space.pal.sig.injection.ApplicationModule;
import space.pal.sig.injection.DaggerApplicationComponent;
import space.pal.sig.injection.DatabaseModule;
import space.pal.sig.service.notification.SpaceAlarmReceiver;
import space.pal.sig.service.notification.SpaceNotificationManager;

import static space.pal.sig.service.notification.SpaceAlarmReceiver.SPACE_ALARM_ACTION;

/**
 * SpaceNow
 * Created by Catalin on 7/17/2020
 **/
public class Space extends Application {

    private static ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        SpaceNotificationManager.createNotificationChannel(getApplicationContext());
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .apiServiceModule(new ApiServiceModule())
                .databaseModule(new DatabaseModule())
                .build();
        registerAlarmReceiver();
        setupDownloadManager();
    }

    public static ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

    private void registerAlarmReceiver() {
        registerReceiver(new SpaceAlarmReceiver(), new IntentFilter(SPACE_ALARM_ACTION));
    }

    private void setupDownloadManager() {
        WorkManager manager = WorkManager.getInstance(this);
        Constraints constraints = new Constraints.Builder()
                .setRequiresBatteryNotLow(true)
                .setRequiredNetworkType(NetworkType.UNMETERED)
                .build();
    }
}
