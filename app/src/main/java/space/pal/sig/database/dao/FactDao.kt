package space.pal.sig.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import space.pal.sig.model.entity.Fact

/**
 * SpaceNow
 * Created by Catalin on 12/24/2020
 **/
@Dao
interface FactDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(fact: Fact)

    @Query("SELECT * FROM facts")
    fun findAll(): LiveData<MutableList<Fact>>

}