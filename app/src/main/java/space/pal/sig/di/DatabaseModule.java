package space.pal.sig.di;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import space.pal.sig.db.ApodDao;
import space.pal.sig.db.FactDao;
import space.pal.sig.db.FeedDao;
import space.pal.sig.db.GlossaryDao;
import space.pal.sig.db.SpaceDatabase;

@Module
public class DatabaseModule {

    @Provides
    @Singleton
    SpaceDatabase spaceDatabase(Context context) {
        return SpaceDatabase.getInstance(context);
    }

    @Provides
    @Singleton
    ApodDao apodDao(SpaceDatabase spaceDatabase) {
        return spaceDatabase.apodDao();
    }

    @Provides
    @Singleton
    GlossaryDao glossaryDao(SpaceDatabase spaceDatabase) {
        return spaceDatabase.glossaryDao();
    }

    @Provides
    @Singleton
    FeedDao feedDao(SpaceDatabase spaceDatabase) {
        return spaceDatabase.feedDao();
    }

    @Provides
    @Singleton
    FactDao factDao(SpaceDatabase spaceDatabase) {
        return spaceDatabase.factDao();
    }

}
