package space.pal.sig.service.download;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.io.IOException;

import retrofit2.Response;
import space.pal.sig.Space;
import space.pal.sig.injection.ApplicationComponent;
import space.pal.sig.repository.ApodRepository;
import space.pal.sig.repository.dto.ApodDto;
import space.pal.sig.repository.service.NasaService;

import static space.pal.sig.repository.service.NasaService.API_KEY;

/**
 * SpaceNow
 * Created by Catalin on 7/24/2020
 **/
public class ApodSyncManager extends Worker {

    public ApodSyncManager(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        ApplicationComponent component = Space.getApplicationComponent();
        NasaService service = component.nasaService();
        ApodRepository repository = component.apodRepository();
        try {
            downloadApod(service, repository);
        } catch (IOException e) {
            e.printStackTrace();
            return Result.retry();
        }
        return Result.success();
    }

    private void downloadApod(NasaService service, ApodRepository repository) throws IOException {
        Response<ApodDto> response = service.getPictureOfTheDay(API_KEY).execute();
        ApodDto apodDto = response.body();
        if (apodDto == null) return;
        repository.insertOrUpdate(apodDto.toApod()).subscribe();
    }
}
