package space.pal.sig.model.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import space.pal.sig.model.Launch;
import space.pal.sig.model.News;
import space.pal.sig.model.database.converters.DateConverter;
import space.pal.sig.model.database.converters.LaunchConverters;
import space.pal.sig.model.database.converters.LaunchStatusConverter;
import space.pal.sig.model.database.converters.MediaTypeConverter;
import space.pal.sig.model.database.converters.NewsSourceConverter;

/**
 * SpaceNow
 * Created by Catalin on 7/18/2020
 **/
@Database(entities = {
        News.class,
        Launch.class
}, version = 1, exportSchema = false)
@TypeConverters({
        DateConverter.class,
        NewsSourceConverter.class,
        LaunchStatusConverter.class,
        LaunchConverters.class,
        MediaTypeConverter.class
})
public abstract class SpaceDatabase extends RoomDatabase {

    public abstract NewsDao newsDao();

    public abstract LaunchDao launchDao();

    public abstract ApodDao apodDao();

    private static SpaceDatabase INSTANCE;

    public static SpaceDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SpaceDatabase.class) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        SpaceDatabase.class, "space_db")
                        .fallbackToDestructiveMigration()
                        .addCallback(callback)
                        .build();
            }
        }
        return INSTANCE;
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
