package space.pal.sig.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import space.pal.sig.database.converters.DateConverter
import space.pal.sig.database.dao.ApodDao
import space.pal.sig.model.AstronomyPictureOfTheDay

/**
 * SpaceNow
 * Created by Catalin on 12/20/2020
 **/
@Database(entities = [
    AstronomyPictureOfTheDay::class
], version = 1, exportSchema = false)
@TypeConverters(
        DateConverter::class
)
abstract class SpaceNowDatabase : RoomDatabase() {

    abstract fun apodDao(): ApodDao

    companion object {
        private lateinit var INSTANCE: SpaceNowDatabase
        fun getInstance(context: Context): SpaceNowDatabase {
            if (!::INSTANCE.isInitialized) {
                synchronized(SpaceNowDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            SpaceNowDatabase::class.java,
                            "space_now_db"
                    ).fallbackToDestructiveMigration().build()
                }
            }
            return INSTANCE
        }
    }

}