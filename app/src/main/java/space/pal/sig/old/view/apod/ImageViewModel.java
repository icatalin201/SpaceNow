package space.pal.sig.old.view.apod;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import space.pal.sig.old.model.Apod;
import space.pal.sig.old.repository.ApodRepository;

/**
 * SpaceNow
 * Created by Catalin on 7/28/2020
 **/
public class ImageViewModel extends AndroidViewModel {

    public static final String IMAGE_URL = "image.url";
    private final ApodRepository apodRepository;
    private final MediatorLiveData<Apod> image = new MediatorLiveData<>();
    private final WorkManager workManager;

    public ImageViewModel(@NonNull Application application, ApodRepository apodRepository) {
        super(application);
        this.apodRepository = apodRepository;
        workManager = WorkManager.getInstance(application);
    }

    public void loadImage(String id) {
        image.addSource(apodRepository.findById(id), image::setValue);
    }

    public void downloadImage(String url) {
        Data inputData = new Data
                .Builder()
                .putString(IMAGE_URL, url)
                .build();
        WorkRequest workRequest = new OneTimeWorkRequest
                .Builder(DownloadImageWorker.class)
                .setInputData(inputData)
                .build();
        workManager.enqueue(workRequest);
    }

    public LiveData<Apod> getImage() {
        return image;
    }
}
