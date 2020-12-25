package space.pal.sig.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import space.pal.sig.model.entity.News
import space.pal.sig.model.entity.NewsSource

/**
 * SpaceNow
 * Created by Catalin on 12/24/2020
 **/
@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(news: News)

    @Query("SELECT * FROM news WHERE source = :source ORDER BY date DESC")
    fun findAllBySource(source: NewsSource): LiveData<MutableList<News>>

    @Query("SELECT * FROM news ORDER BY date DESC")
    fun findAll(): LiveData<MutableList<News>>

    @Query("SELECT * FROM news WHERE title = :title")
    fun findByTitle(title: String): LiveData<News>

}