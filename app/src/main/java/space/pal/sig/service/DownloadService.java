package space.pal.sig.service;

import android.app.job.JobParameters;
import android.app.job.JobService;

import space.pal.sig.Space;
import space.pal.sig.repository.NewsRepository;
import space.pal.sig.repository.service.HubbleService;

/**
 * SpaceNow
 * Created by Catalin on 7/19/2020
 **/
public class DownloadService extends JobService implements CompleteListener {

    private final DownloadTask downloadTask;
    private JobParameters jobParameters;

    public DownloadService() {
        HubbleService hubbleService = Space.getApplicationComponent().hubbleService();
        NewsRepository repository = Space.getApplicationComponent().newsRepository();
        downloadTask = new DownloadTask(hubbleService, repository, this);
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        this.jobParameters = params;
        downloadTask.execute();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        if (downloadTask != null) {
            downloadTask.cancel(true);
        }
        return true;
    }

    @Override
    public void onComplete() {
        jobFinished(jobParameters, true);
        SpaceNotificationManager.createNotification(this, "Sync complete!");
    }

}
