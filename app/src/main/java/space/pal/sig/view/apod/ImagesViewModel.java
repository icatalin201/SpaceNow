package space.pal.sig.view.apod;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

import space.pal.sig.model.Apod;
import space.pal.sig.repository.ApodRepository;

/**
 * SpaceNow
 * Created by Catalin on 7/28/2020
 **/
public class ImagesViewModel extends AndroidViewModel {

    private final LiveData<PagedList<Apod>> apodList;

    public ImagesViewModel(@NonNull Application application, ApodRepository apodRepository) {
        super(application);
        apodList = apodRepository.findAllImages(30);
    }

    public LiveData<PagedList<Apod>> getApodList() {
        return apodList;
    }
}
