package space.pal.sig.old.injection;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import space.pal.sig.old.model.database.ApodDao;
import space.pal.sig.old.model.database.FactDao;
import space.pal.sig.old.model.database.LaunchDao;
import space.pal.sig.old.model.database.NewsDao;
import space.pal.sig.old.model.database.SpaceDatabase;

@Module
public class DatabaseModule {

    @Provides
    @Singleton
    SpaceDatabase spaceDatabase(Context context) {
        return SpaceDatabase.getInstance(context);
    }

    @Provides
    @Singleton
    NewsDao newsDao(SpaceDatabase spaceDatabase) {
        return spaceDatabase.newsDao();
    }

    @Provides
    @Singleton
    LaunchDao launchDao(SpaceDatabase spaceDatabase) {
        return spaceDatabase.launchDao();
    }

    @Provides
    @Singleton
    ApodDao apodDao(SpaceDatabase spaceDatabase) {
        return spaceDatabase.apodDao();
    }

    @Provides
    @Singleton
    FactDao factDao(SpaceDatabase spaceDatabase) {
        return spaceDatabase.factDao();
    }

}
