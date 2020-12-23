package space.pal.sig.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import space.pal.sig.model.entity.Launch
import space.pal.sig.model.entity.LaunchWithData

/**
 * SpaceNow
 * Created by Catalin on 12/23/2020
 **/
@Dao
interface LaunchDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(launch: Launch)

    @Query("SELECT * FROM launches")
    fun findAll(): LiveData<MutableList<LaunchWithData>>

    @Query("SELECT * FROM launches WHERE id = :id")
    fun findById(id: String): LiveData<LaunchWithData>

    @Query("""
        SELECT * FROM launches 
        WHERE dateUtc >= DATETIME('now') 
        ORDER BY dateUnix ASC LIMIT 1
        """)
    fun findNextLaunch(): LiveData<LaunchWithData>

    @Query("""
        SELECT * FROM launches 
        WHERE dateUtc >= DATETIME('now') 
        ORDER BY dateUnix ASC
        """)
    fun findUpcomingLaunches(): LiveData<MutableList<LaunchWithData>>

    @Query("""
        SELECT * FROM launches 
        WHERE dateUtc < DATETIME('now') 
        ORDER BY dateUnix DESC
        """)
    fun findPastLaunches(): LiveData<MutableList<LaunchWithData>>
}