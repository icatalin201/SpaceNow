package space.pal.sig.old.injection;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import space.pal.sig.old.model.database.ApodDao;
import space.pal.sig.old.model.database.FactDao;
import space.pal.sig.old.model.database.LaunchDao;
import space.pal.sig.old.model.database.NewsDao;
import space.pal.sig.old.repository.ApodRepository;
import space.pal.sig.old.repository.FactRepository;
import space.pal.sig.old.repository.LaunchesRepository;
import space.pal.sig.old.repository.NewsRepository;

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

    @Provides
    @Singleton
    ApodRepository apodRepository(ApodDao apodDao) {
        return new ApodRepository(apodDao);
    }

    @Provides
    @Singleton
    FactRepository factRepository(FactDao factDao) {
        return new FactRepository(factDao);
    }

}
