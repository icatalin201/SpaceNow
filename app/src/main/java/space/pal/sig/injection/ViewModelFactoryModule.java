package space.pal.sig.injection;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import space.pal.sig.repository.ApodRepository;
import space.pal.sig.repository.FactRepository;
import space.pal.sig.repository.LaunchesRepository;
import space.pal.sig.repository.NewsRepository;
import space.pal.sig.util.SharedPreferencesUtil;
import space.pal.sig.util.SpaceExecutors;
import space.pal.sig.view.apod.ApodViewModelFactory;
import space.pal.sig.view.apod.ImageViewModelFactory;
import space.pal.sig.view.apod.ImagesViewModelFactory;
import space.pal.sig.view.facts.FactsViewModelFactory;
import space.pal.sig.view.launches.LaunchViewModelFactory;
import space.pal.sig.view.launches.LaunchesViewModelFactory;
import space.pal.sig.view.main.MainViewModelFactory;
import space.pal.sig.view.news.NewsViewModelFactory;
import space.pal.sig.view.news.SingleNewsViewModelFactory;
import space.pal.sig.view.splash.SplashViewModelFactory;

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
    LaunchViewModelFactory launchViewModelFactory(Application application,
                                                  LaunchesRepository launchesRepository,
                                                  SpaceExecutors spaceExecutors,
                                                  SharedPreferencesUtil sharedPreferencesUtil) {
        return new LaunchViewModelFactory(application, launchesRepository, spaceExecutors, sharedPreferencesUtil);
    }

    @Provides
    @Singleton
    MainViewModelFactory mainViewModelFactory(Application application) {
        return new MainViewModelFactory(application);
    }

    @Provides
    @Singleton
    SplashViewModelFactory splashViewModelFactory(Application application,
                                                  SharedPreferencesUtil sharedPreferencesUtil) {
        return new SplashViewModelFactory(application, sharedPreferencesUtil);
    }

    @Provides
    @Singleton
    ApodViewModelFactory apodViewModelFactory(Application application,
                                              ApodRepository apodRepository) {
        return new ApodViewModelFactory(application, apodRepository);
    }

    @Provides
    @Singleton
    ImageViewModelFactory imageViewModelFactory(Application application,
                                                ApodRepository apodRepository) {
        return new ImageViewModelFactory(application, apodRepository);
    }

    @Provides
    @Singleton
    ImagesViewModelFactory imagesViewModelFactory(Application application,
                                                  ApodRepository apodRepository) {
        return new ImagesViewModelFactory(application, apodRepository);
    }

    @Provides
    @Singleton
    SingleNewsViewModelFactory singleNewsViewModelFactory(Application application,
                                                          NewsRepository newsRepository) {
        return new SingleNewsViewModelFactory(application, newsRepository);
    }

    @Provides
    @Singleton
    FactsViewModelFactory factsViewModelFactory(Application application,
                                                FactRepository factRepository) {
        return new FactsViewModelFactory(application, factRepository);
    }

}
