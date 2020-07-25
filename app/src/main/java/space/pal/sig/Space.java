package space.pal.sig;

import android.app.Application;
import android.content.IntentFilter;

import space.pal.sig.injection.ApiServiceModule;
import space.pal.sig.injection.ApplicationComponent;
import space.pal.sig.injection.ApplicationModule;
import space.pal.sig.injection.DaggerApplicationComponent;
import space.pal.sig.injection.DatabaseModule;
import space.pal.sig.service.download.DownloadManager;
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
        DownloadManager.createSyncQueue(this);
    }

    public static ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

    private void registerAlarmReceiver() {
        registerReceiver(new SpaceAlarmReceiver(), new IntentFilter(SPACE_ALARM_ACTION));
    }
}
