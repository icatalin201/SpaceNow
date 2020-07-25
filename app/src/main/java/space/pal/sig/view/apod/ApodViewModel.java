package space.pal.sig.view.apod;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.Date;
import java.util.List;

import space.pal.sig.model.Apod;
import space.pal.sig.repository.ApodRepository;

/**
 * SpaceNow
 * Created by Catalin on 7/24/2020
 **/
public class ApodViewModel extends AndroidViewModel {

    private final ApodRepository apodRepository;
    private final LiveData<Apod> todayApod;
    private final LiveData<List<Apod>> apodList;

    public ApodViewModel(@NonNull Application application, ApodRepository apodRepository) {
        super(application);
        this.apodRepository = apodRepository;
        todayApod = apodRepository.findByDate(new Date());
        apodList = apodRepository.findAllBeforeOfDateWithLimit(new Date(), 30);
    }

    public LiveData<Apod> getTodayApod() {
        return todayApod;
    }

    public LiveData<List<Apod>> getApodList() {
        return apodList;
    }
}
