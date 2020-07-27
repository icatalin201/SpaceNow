package space.pal.sig.view.launches;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import space.pal.sig.model.Launch;
import space.pal.sig.repository.LaunchesRepository;

/**
 * SpaceNow
 * Created by Catalin on 7/26/2020
 **/
public class LaunchViewModel extends AndroidViewModel {

    private final LaunchesRepository launchesRepository;
    private MediatorLiveData<Launch> launch = new MediatorLiveData<>();

    public LaunchViewModel(@NonNull Application application, LaunchesRepository launchesRepository) {
        super(application);
        this.launchesRepository = launchesRepository;
    }

    public void loadLaunch(long id) {
        launch.addSource(launchesRepository.findById(id), launch::setValue);
    }

    public MutableLiveData<Launch> getLaunch() {
        return launch;
    }
}
