package space.pal.sig.old.service.download;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.io.IOException;

import space.pal.sig.old.Space;
import space.pal.sig.old.injection.ApplicationComponent;
import space.pal.sig.old.repository.ApodRepository;
import space.pal.sig.old.repository.LaunchesRepository;
import space.pal.sig.old.repository.NewsRepository;
import space.pal.sig.old.repository.service.HubbleService;
import space.pal.sig.old.repository.service.LaunchService;
import space.pal.sig.old.repository.service.NasaService;

import static space.pal.sig.old.service.download.DataSyncUtil.downloadApod;
import static space.pal.sig.old.service.download.DataSyncUtil.downloadEsaNews;
import static space.pal.sig.old.service.download.DataSyncUtil.downloadHubbleNews;
import static space.pal.sig.old.service.download.DataSyncUtil.downloadJwNews;
import static space.pal.sig.old.service.download.DataSyncUtil.downloadNextLaunches;
import static space.pal.sig.old.service.download.DataSyncUtil.downloadStNews;
import static space.pal.sig.old.service.notification.SpaceNotificationManager.createNotification;

/**
 * SpaceNow
 * Created by Catalin on 7/31/2020
 **/
public class DataSyncManager extends Worker {

    public static final String DATA_SYNC_WORKER_TAG = "data_sync_worker_tag";

    public DataSyncManager(@NonNull Context context,
                           @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        ApplicationComponent component = Space.getApplicationComponent();
        NasaService nasaService = component.nasaService();
        ApodRepository apodRepository = component.apodRepository();
        HubbleService hubbleService = component.hubbleService();
        NewsRepository newsRepository = component.newsRepository();
        LaunchService launchService = component.launchService();
        LaunchesRepository launchesRepository = component.launchesRepository();
        try {
            downloadApod(nasaService, apodRepository);
            downloadEsaNews(hubbleService, newsRepository);
            downloadHubbleNews(hubbleService, newsRepository);
            downloadJwNews(hubbleService, newsRepository);
            downloadStNews(hubbleService, newsRepository);
            downloadNextLaunches(launchService, launchesRepository);
            createNotification(getApplicationContext(), "Sync finished successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            return Result.failure();
        }
        return Result.success();
    }
}
