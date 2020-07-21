package space.pal.sig.injection;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import space.pal.sig.model.database.LaunchDao;
import space.pal.sig.model.database.NewsDao;
import space.pal.sig.repository.LaunchesRepository;
import space.pal.sig.repository.NewsRepository;

/**
 * SpaceNow
 * Created by Catalin on 7/19/2020
 **/
@Module
public class RepositoryModule {

    @Provides
    @Singleton
    NewsRepository newsRepository(NewsDao newsDao) {
        return new NewsRepository(newsDao);
    }

    @Provides
    @Singleton
    LaunchesRepository launchesRepository(LaunchDao launchDao) {
        return new LaunchesRepository(launchDao);
    }

}
