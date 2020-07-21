package space.pal.sig;

import android.app.Application;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.IntentFilter;

import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import space.pal.sig.injection.ApiServiceModule;
import space.pal.sig.injection.ApplicationComponent;
import space.pal.sig.injection.ApplicationModule;
import space.pal.sig.injection.DaggerApplicationComponent;
import space.pal.sig.injection.DatabaseModule;
import space.pal.sig.service.DownloadService;
import space.pal.sig.service.LaunchesDownloadManager;
import space.pal.sig.service.NewsDownloadManager;
import space.pal.sig.service.SpaceAlarmReceiver;
import space.pal.sig.service.SpaceNotificationManager;

import static space.pal.sig.service.SpaceAlarmReceiver.SPACE_ALARM_ACTION;

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

    private void setupDownloadService() {
        ComponentName componentName = new ComponentName(this, DownloadService.class.getName());
        JobInfo jobInfo = new JobInfo.Builder(1, componentName)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                .setPeriodic(24 * 60 * 60 * 1000)
                .build();
        JobScheduler jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        jobScheduler.schedule(jobInfo);
    }

    private void setupDownloadManager() {
        WorkManager manager = WorkManager.getInstance(this);
        Constraints constraints = new Constraints.Builder()
                .setRequiresBatteryNotLow(true)
                .setRequiredNetworkType(NetworkType.UNMETERED)
                .build();
        PeriodicWorkRequest newsWork = new PeriodicWorkRequest.Builder(NewsDownloadManager.class,
                1, TimeUnit.DAYS)
                .setConstraints(constraints)
                .build();
        PeriodicWorkRequest launchesWork = new PeriodicWorkRequest.Builder(LaunchesDownloadManager.class,
                1, TimeUnit.DAYS)
                .setConstraints(constraints)
                .build();
        manager.enqueue(launchesWork);
        manager.enqueue(newsWork);
    }
}
