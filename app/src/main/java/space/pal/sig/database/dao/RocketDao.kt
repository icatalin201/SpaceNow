package space.pal.sig.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import space.pal.sig.model.entity.Rocket

/**
 * SpaceNow
 * Created by Catalin on 12/23/2020
 */
@Dao
interface RocketDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(rocket: Rocket)

    @Query("SELECT * FROM rockets WHERE id = :id")
    fun findById(id: String): LiveData<Rocket>

    @Query("SELECT * FROM rockets")
    fun findAll(): LiveData<MutableList<Rocket>>
}