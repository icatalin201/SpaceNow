package space.pal.sig.injection;

import javax.inject.Singleton;

import dagger.Component;
import space.pal.sig.repository.ApodRepository;
import space.pal.sig.repository.LaunchesRepository;
import space.pal.sig.repository.NewsRepository;
import space.pal.sig.repository.service.HubbleService;
import space.pal.sig.repository.service.LaunchService;
import space.pal.sig.repository.service.NasaService;
import space.pal.sig.view.apod.ApodFragment;
import space.pal.sig.view.launches.LaunchActivity;
import space.pal.sig.view.launches.LaunchesFragment;
import space.pal.sig.view.main.MainActivity;
import space.pal.sig.view.news.NewsFragment;
import space.pal.sig.view.splash.SplashActivity;

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

    void inject(NewsFragment newsFragment);

    void inject(LaunchesFragment launchesFragment);

    void inject(MainActivity mainActivity);

    void inject(SplashActivity splashActivity);

    void inject(ApodFragment apodFragment);

    void inject(LaunchActivity launchActivity);
}
