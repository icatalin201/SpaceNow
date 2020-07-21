package space.pal.sig.injection;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import space.pal.sig.repository.LaunchesRepository;
import space.pal.sig.repository.NewsRepository;
import space.pal.sig.view.launches.LaunchesViewModelFactory;
import space.pal.sig.view.main.MainViewModelFactory;
import space.pal.sig.view.news.NewsViewModelFactory;

/**
 * SpaceNow
 * Created by Catalin on 7/19/2020
 **/
@Module
public class ViewModelFactoryModule {

    @Provides
    @Singleton
    NewsViewModelFactory newsViewModelFactory(Application application,
                                              NewsRepository newsRepository) {
        return new NewsViewModelFactory(application, newsRepository);
    }

    @Provides
    @Singleton
    LaunchesViewModelFactory launchesViewModelFactory(Application application,
                                                      LaunchesRepository launchesRepository) {
        return new LaunchesViewModelFactory(application, launchesRepository);
    }

    @Provides
    @Singleton
    MainViewModelFactory mainViewModelFactory(Application application) {
        return new MainViewModelFactory(application);
    }

}
