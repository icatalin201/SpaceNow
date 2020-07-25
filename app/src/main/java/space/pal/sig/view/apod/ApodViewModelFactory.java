package space.pal.sig.view.apod;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import lombok.RequiredArgsConstructor;
import space.pal.sig.repository.ApodRepository;

/**
 * SpaceNow
 * Created by Catalin on 7/25/2020
 **/
@RequiredArgsConstructor
public class ApodViewModelFactory implements ViewModelProvider.Factory {

    private final Application application;
    private final ApodRepository apodRepository;

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ApodViewModel(application, apodRepository);
    }
}
