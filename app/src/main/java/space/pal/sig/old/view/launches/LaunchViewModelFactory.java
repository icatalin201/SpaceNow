package space.pal.sig.old.view.launches;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import lombok.RequiredArgsConstructor;
import space.pal.sig.old.repository.LaunchesRepository;
import space.pal.sig.old.util.SharedPreferencesUtil;
import space.pal.sig.old.util.SpaceExecutors;

/**
 * SpaceNow
 * Created by Catalin on 7/26/2020
 **/
@RequiredArgsConstructor
public class LaunchViewModelFactory implements ViewModelProvider.Factory {

    private final Application application;
    private final LaunchesRepository launchesRepository;
    private final SpaceExecutors spaceExecutors;
    private final SharedPreferencesUtil sharedPreferencesUtil;

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new LaunchViewModel(application, launchesRepository, spaceExecutors, sharedPreferencesUtil);
    }
}
