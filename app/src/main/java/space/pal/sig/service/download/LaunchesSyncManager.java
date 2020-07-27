package space.pal.sig.service.download;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.io.IOException;

import retrofit2.Response;
import space.pal.sig.Space;
import space.pal.sig.injection.ApplicationComponent;
import space.pal.sig.repository.LaunchesRepository;
import space.pal.sig.repository.dto.LaunchDto;
import space.pal.sig.repository.dto.LaunchResponse;
import space.pal.sig.repository.service.LaunchService;

/**
 * SpaceNow
 * Created by Catalin on 7/19/2020
 **/
public class LaunchesSyncManager extends Worker {

    public LaunchesSyncManager(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        ApplicationComponent component = Space.getApplicationComponent();
        LaunchService service = component.launchService();
        LaunchesRepository repository = component.launchesRepository();
        try {
            downloadNextLaunches(service, repository);
        } catch (IOException e) {
            e.printStackTrace();
            return Result.retry();
        }
        return Result.Success.success();
    }

    private void downloadNextLaunches(LaunchService service, LaunchesRepository repository) throws IOException {
        Response<LaunchResponse> response = service
                .getNextLaunches(30).execute();
        LaunchResponse launchResponse = response.body();
        assert launchResponse != null;
        for (LaunchDto launchDto : launchResponse.getLaunches()) {
            repository.insertOrUpdate(launchDto.toLaunchModel()).subscribe();
        }
    }
}
