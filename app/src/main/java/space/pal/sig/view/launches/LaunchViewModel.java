package space.pal.sig.view.launches;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.Set;

import space.pal.sig.model.Launch;
import space.pal.sig.repository.LaunchesRepository;
import space.pal.sig.util.SharedPreferencesUtil;
import space.pal.sig.util.SpaceExecutors;

/**
 * SpaceNow
 * Created by Catalin on 7/26/2020
 **/
public class LaunchViewModel extends AndroidViewModel {

    private final LaunchesRepository launchesRepository;
    private final SpaceExecutors spaceExecutors;
    private final SharedPreferencesUtil sharedPreferencesUtil;
    private MediatorLiveData<Launch> launch = new MediatorLiveData<>();

    public LaunchViewModel(@NonNull Application application,
                           LaunchesRepository launchesRepository,
                           SpaceExecutors spaceExecutors,
                           SharedPreferencesUtil sharedPreferencesUtil) {
        super(application);
        this.launchesRepository = launchesRepository;
        this.spaceExecutors = spaceExecutors;
        this.sharedPreferencesUtil = sharedPreferencesUtil;
    }

    public void loadLaunch(long id) {
        launch.addSource(launchesRepository.findById(id), launch::setValue);
    }

    public MutableLiveData<Launch> getLaunch() {
        return launch;
    }

    public void toggleFavorite(Launch launch) {
        launch.setFavorite(!launch.getFavorite());
        spaceExecutors.diskIO().execute(() ->
                launchesRepository.insertOrUpdate(launch).subscribe());
    }

    public void toggleNotifications(boolean isOn, long launchId) {
        Set<String> notifications = sharedPreferencesUtil.getSet("notifications");
        if (isOn) {
            notifications.add(String.valueOf(launchId));
        } else {
            notifications.remove(String.valueOf(launchId));
        }
        sharedPreferencesUtil.storeSet("notifications", notifications);
    }

    public boolean hasNotifications(long launchId) {
        return sharedPreferencesUtil.getSet("notifications").contains(String.valueOf(launchId));
    }
}
