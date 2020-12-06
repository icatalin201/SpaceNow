package space.pal.sig.old.view.news;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import lombok.RequiredArgsConstructor;
import space.pal.sig.old.repository.NewsRepository;

/**
 * SpaceNow
 * Created by Catalin on 7/19/2020
 **/
@RequiredArgsConstructor
public class NewsViewModelFactory implements ViewModelProvider.Factory {

    private final Application application;
    private final NewsRepository newsRepository;

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new NewsViewModel(application, newsRepository);
    }
}
