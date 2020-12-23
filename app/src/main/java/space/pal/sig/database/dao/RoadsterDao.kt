package space.pal.sig.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import space.pal.sig.model.entity.Roadster

/**
 * SpaceNow
 * Created by Catalin on 12/23/2020
 **/
@Dao
interface RoadsterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(roadster: Roadster)

    @Query("SELECT * FROM roadster LIMIT 1")
    fun find(): LiveData<Roadster>

}