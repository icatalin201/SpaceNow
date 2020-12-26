package space.pal.sig.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import space.pal.sig.model.entity.CrewMember

/**
 * SpaceNow
 * Created by Catalin on 12/26/2020
 **/
@Dao
interface CrewMemberDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(crewMember: CrewMember)

}