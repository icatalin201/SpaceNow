package space.pal.sig.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import space.pal.sig.model.entity.AstronomyPictureOfTheDay

/**
 * SpaceNow
 * Created by Catalin on 12/20/2020
 **/
@Dao
interface ApodDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(astronomyPictureOfTheDay: AstronomyPictureOfTheDay)

    @Query("SELECT * FROM astronomy_pictures_of_the_day WHERE date = :date")
    fun findByDate(date: String): LiveData<AstronomyPictureOfTheDay>

    @Query("SELECT * FROM astronomy_pictures_of_the_day ORDER BY date DESC LIMIT 1")
    fun findLatest(): LiveData<AstronomyPictureOfTheDay>

}