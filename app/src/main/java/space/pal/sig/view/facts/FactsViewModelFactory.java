package space.pal.sig.view.facts;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import lombok.RequiredArgsConstructor;
import space.pal.sig.repository.FactRepository;

/**
 * SpaceNow
 * Created by Catalin on 7/29/2020
 **/
@RequiredArgsConstructor
public class FactsViewModelFactory implements ViewModelProvider.Factory {

    private final Application application;
    private final FactRepository factRepository;

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new FactsViewModel(application, factRepository);
    }
}
