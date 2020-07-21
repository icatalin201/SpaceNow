package space.pal.sig.injection;

import javax.inject.Singleton;

import dagger.Component;
import space.pal.sig.repository.LaunchesRepository;
import space.pal.sig.repository.NewsRepository;
import space.pal.sig.repository.service.HubbleService;
import space.pal.sig.repository.service.LaunchService;
import space.pal.sig.view.launches.LaunchesFragment;
import space.pal.sig.view.main.MainActivity;
import space.pal.sig.view.news.NewsFragment;

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

    NewsRepository newsRepository();

    LaunchesRepository launchesRepository();

    void inject(NewsFragment newsFragment);

    void inject(LaunchesFragment launchesFragment);

    void inject(MainActivity mainActivity);
}
