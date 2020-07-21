package space.pal.sig.view.main;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import lombok.RequiredArgsConstructor;

/**
 * SpaceNow
 * Created by Catalin on 7/21/2020
 **/
@RequiredArgsConstructor
public class MainViewModelFactory implements ViewModelProvider.Factory {

    private final Application application;

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MainViewModel(application);
    }
}
