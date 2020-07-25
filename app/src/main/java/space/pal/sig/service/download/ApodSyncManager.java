package space.pal.sig.service.download;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

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
        return null;
    }
}
