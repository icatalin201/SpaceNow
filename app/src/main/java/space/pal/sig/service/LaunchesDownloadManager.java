package space.pal.sig.service;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.io.IOException;
import java.util.Date;

import retrofit2.Response;
import space.pal.sig.Space;
import space.pal.sig.injection.ApplicationComponent;
import space.pal.sig.repository.LaunchesRepository;
import space.pal.sig.repository.dto.LaunchDto;
import space.pal.sig.repository.dto.LaunchResponse;
import space.pal.sig.repository.service.LaunchService;
import space.pal.sig.util.DateUtil;

/**
 * SpaceNow
 * Created by Catalin on 7/19/2020
 **/
public class LaunchesDownloadManager extends Worker {

    public LaunchesDownloadManager(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        ApplicationComponent component = Space.getApplicationComponent();
        LaunchService service = component.launchService();
        LaunchesRepository repository = component.launchesRepository();
        try {
            downloadPastLaunches(service, repository);
            downloadNextLaunches(service, repository);
            SpaceNotificationManager.createNotification(getApplicationContext(),
                    "Launches Sync Complete");
        } catch (IOException e) {
            e.printStackTrace();
            return Result.retry();
        }
        return Result.success();
    }

    private void downloadPastLaunches(LaunchService service,
                                      LaunchesRepository repository) throws IOException {
        String endDate = DateUtil.formatDate(new Date(), "yyyy-MM-dd");
        Response<LaunchResponse> response = service
                .getLaunches(0, 10000, "1950-01-01", endDate).execute();
        LaunchResponse launchResponse = response.body();
        assert launchResponse != null;
        for (LaunchDto launchDto : launchResponse.getLaunches()) {
            repository.insertOrUpdate(launchDto.toLaunchModel()).subscribe();
        }
    }

    private void downloadNextLaunches(LaunchService service,
                                      LaunchesRepository repository) throws IOException {
        Response<LaunchResponse> response = service
                .getNextLaunches(1000).execute();
        LaunchResponse launchResponse = response.body();
        assert launchResponse != null;
        for (LaunchDto launchDto : launchResponse.getLaunches()) {
            repository.insertOrUpdate(launchDto.toLaunchModel()).subscribe();
        }
    }
}
