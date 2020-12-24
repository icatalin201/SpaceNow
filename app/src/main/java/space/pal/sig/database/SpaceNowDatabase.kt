package space.pal.sig.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import space.pal.sig.database.converters.*
import space.pal.sig.database.dao.*
import space.pal.sig.model.entity.*

/**
 * SpaceNow
 * Created by Catalin on 12/20/2020
 **/
@Database(entities = [
    Launch::class,
    Roadster::class,
    Rocket::class,
    LaunchPad::class,
    Fact::class,
    News::class,
    AstronomyPictureOfTheDay::class
], version = 1, exportSchema = false)
@TypeConverters(
        UtilConverter::class,
        DateConverter::class,
        DataLinkConverter::class,
        FailureConverter::class,
        LaunchCoreConverter::class,
        LaunchFairingConverter::class,
        PayloadWeightConverter::class,
        PayloadMassConverter::class,
        DiameterConverter::class,
        RocketEnginesConverter::class,
        RocketLandingLegsConverter::class,
        RocketFirstStageConverter::class,
        RocketSecondStageConverter::class,
        NewsSourceConverter::class
)
abstract class SpaceNowDatabase : RoomDatabase() {

    abstract fun apodDao(): ApodDao
    abstract fun launchDao(): LaunchDao
    abstract fun roadsterDao(): RoadsterDao
    abstract fun rocketDao(): RocketDao
    abstract fun launchpadDao(): LaunchPadDao
    abstract fun factDao(): FactDao
    abstract fun newsDao(): NewsDao

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