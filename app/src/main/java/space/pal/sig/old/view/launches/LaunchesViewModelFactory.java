package space.pal.sig.old.view.launches;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import lombok.RequiredArgsConstructor;
import space.pal.sig.old.repository.LaunchesRepository;

/**
 * SpaceNow
 * Created by Catalin on 7/19/2020
 **/
@RequiredArgsConstructor
public class LaunchesViewModelFactory implements ViewModelProvider.Factory {

    private final Application application;
    private final LaunchesRepository launchesRepository;

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new LaunchesViewModel(application, launchesRepository);
    }
}
