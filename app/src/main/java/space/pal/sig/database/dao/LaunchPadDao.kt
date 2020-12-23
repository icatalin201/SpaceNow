package space.pal.sig.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import space.pal.sig.model.entity.LaunchPad

/**
 * SpaceNow
 * Created by Catalin on 12/23/2020
 **/
@Dao
interface LaunchPadDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(launchPad: LaunchPad)

    @Query("SELECT * FROM launchpads WHERE id = :id")
    fun findById(id: String): LiveData<LaunchPad>

}