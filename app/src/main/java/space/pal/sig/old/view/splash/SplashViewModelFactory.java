package space.pal.sig.old.view.splash;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import lombok.RequiredArgsConstructor;
import space.pal.sig.old.util.SharedPreferencesUtil;

/**
 * SpaceNow
 * Created by Catalin on 7/22/2020
 **/
@RequiredArgsConstructor
public class SplashViewModelFactory implements ViewModelProvider.Factory {

    private final Application application;
    private final SharedPreferencesUtil sharedPreferencesUtil;

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new SplashViewModel(application, sharedPreferencesUtil);
    }
}
