package space.pal.sig.service.download;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.io.IOException;
import java.util.Calendar;

import retrofit2.Response;
import space.pal.sig.Space;
import space.pal.sig.injection.ApplicationComponent;
import space.pal.sig.repository.ApodRepository;
import space.pal.sig.repository.dto.ApodDto;
import space.pal.sig.repository.service.NasaService;
import space.pal.sig.service.notification.SpaceNotificationManager;
import space.pal.sig.util.DateUtil;

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
            SpaceNotificationManager.createNotification(getApplicationContext(),
                    "Astronomical picture of the day was just updated!");
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
        Calendar calendar = Calendar.getInstance();
        for (int i = 0; i < 50; i++) {
            calendar.add(Calendar.DAY_OF_YEAR, -i);
            String date = DateUtil.formatDate(calendar.getTime(), "yyyy-MM-dd");
            Response<ApodDto> r = service.getPictureOfTheDay(API_KEY, date).execute();
            ApodDto a = r.body();
            if (a == null) return;
            repository.insertOrUpdate(a.toApod()).subscribe();
        }
    }
}
