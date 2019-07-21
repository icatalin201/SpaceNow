package space.pal.sig.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import space.pal.sig.model.Apod;
import space.pal.sig.model.Fact;
import space.pal.sig.model.Feed;
import space.pal.sig.model.Glossary;

@Database(entities = {
        Apod.class,
        Glossary.class,
        Feed.class,
        Fact.class
}, version = 3, exportSchema = false)
public abstract class SpaceDatabase extends RoomDatabase {

    public abstract ApodDao apodDao();
    public abstract FeedDao feedDao();
    public abstract GlossaryDao glossaryDao();
    public abstract FactDao factDao();

    private static SpaceDatabase appDatabase;

    public static synchronized SpaceDatabase getInstance(Context context) {
        if (appDatabase == null) {
            appDatabase = Room
                    .databaseBuilder(context.getApplicationContext(),
                            SpaceDatabase.class, "space")
                    .fallbackToDestructiveMigration()
                    .addCallback(callback)
                    .build();
        }
        return appDatabase;
    }

    private static RoomDatabase.Callback callback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
        }
    };

}
