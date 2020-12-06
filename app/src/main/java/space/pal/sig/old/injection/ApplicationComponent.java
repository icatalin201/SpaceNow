package space.pal.sig.old.injection;

import javax.inject.Singleton;

import dagger.Component;
import space.pal.sig.old.repository.ApodRepository;
import space.pal.sig.old.repository.FactRepository;
import space.pal.sig.old.repository.LaunchesRepository;
import space.pal.sig.old.repository.NewsRepository;
import space.pal.sig.old.repository.service.HubbleService;
import space.pal.sig.old.repository.service.LaunchService;
import space.pal.sig.old.repository.service.NasaService;
import space.pal.sig.old.util.SharedPreferencesUtil;
import space.pal.sig.old.view.apod.ApodFragment;
import space.pal.sig.old.view.apod.ImageActivity;
import space.pal.sig.old.view.apod.ImagesActivity;
import space.pal.sig.old.view.facts.FactsFragment;
import space.pal.sig.old.view.launches.LaunchActivity;
import space.pal.sig.old.view.launches.LaunchesFragment;
import space.pal.sig.old.view.main.MainActivity;
import space.pal.sig.old.view.news.NewsActivity;
import space.pal.sig.old.view.news.NewsFragment;
import space.pal.sig.old.view.splash.SplashActivity;

@Singleton
@Component(modules = {
        ApiServiceModule.class,
        ApplicationModule.class,
        DatabaseModule.class,
        RepositoryModule.class,
        ViewModelFactoryModule.class
})
public interface ApplicationComponent {

    HubbleService hubbleService();

    LaunchService launchService();

    NasaService nasaService();

    NewsRepository newsRepository();

    LaunchesRepository launchesRepository();

    ApodRepository apodRepository();

    FactRepository factRepository();

    SharedPreferencesUtil sharedPreferencesUtil();

    void inject(NewsFragment newsFragment);

    void inject(LaunchesFragment launchesFragment);

    void inject(MainActivity mainActivity);

    void inject(SplashActivity splashActivity);

    void inject(ApodFragment apodFragment);

    void inject(LaunchActivity launchActivity);

    void inject(ImageActivity imageActivity);

    void inject(ImagesActivity imagesActivity);

    void inject(NewsActivity newsActivity);

    void inject(FactsFragment factsFragment);
}
