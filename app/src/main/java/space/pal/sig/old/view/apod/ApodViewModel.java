package space.pal.sig.old.view.apod;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import space.pal.sig.old.model.Apod;
import space.pal.sig.old.repository.ApodRepository;

/**
 * SpaceNow
 * Created by Catalin on 7/24/2020
 **/
public class ApodViewModel extends AndroidViewModel {

    private final ApodRepository apodRepository;
    private final MutableLiveData<Apod> todayApod = new MutableLiveData<>();
    private final MediatorLiveData<List<Apod>> apodList = new MediatorLiveData<>();

    public ApodViewModel(@NonNull Application application, ApodRepository apodRepository) {
        super(application);
        this.apodRepository = apodRepository;
        apodList.addSource(apodRepository.findAllWithLimit(11), apodList -> {
            Apod apod = apodList.get(0);
            apodList.remove(0);
            this.todayApod.setValue(apod);
            this.apodList.setValue(apodList);
        });
    }

    public LiveData<Apod> getTodayApod() {
        return todayApod;
    }

    public LiveData<List<Apod>> getApodList() {
        return apodList;
    }
}
