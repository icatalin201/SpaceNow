package space.pal.sig.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import space.pal.sig.model.AstronomyPictureOfTheDay

/**
 * SpaceNow
 * Created by Catalin on 12/20/2020
 **/
@Dao
interface ApodDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(astronomyPictureOfTheDay: AstronomyPictureOfTheDay)

    @Query("select * from astronomy_pictures_of_the_day where mediaType = 'IMAGE' order by date desc")
    fun findAllImages(): LiveData<MutableList<AstronomyPictureOfTheDay>>

    @Query("select * from astronomy_pictures_of_the_day where date = :date")
    fun findByDate(date: String): LiveData<AstronomyPictureOfTheDay>

}