package space.pal.sig.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import space.pal.sig.model.entity.CrewMember

/**
 * SpaceNow
 * Created by Catalin on 12/26/2020
 **/
@Dao
interface CrewMemberDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(crewMember: CrewMember)

    @Query("SELECT * FROM crew_members WHERE id in (:ids)")
    fun findAllById(ids: List<String>): LiveData<List<CrewMember>>

}