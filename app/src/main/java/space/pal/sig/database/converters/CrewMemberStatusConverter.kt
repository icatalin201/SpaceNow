package space.pal.sig.database.converters

import androidx.room.TypeConverter
import space.pal.sig.model.dto.CrewMemberStatus

/**
 * SpaceNow
 * Created by Catalin on 12/26/2020
 **/
class CrewMemberStatusConverter {

    @TypeConverter
    fun fromString(string: String): CrewMemberStatus {
        return CrewMemberStatus.valueOf(string)
    }

    @TypeConverter
    fun toString(obj: CrewMemberStatus): String {
        return obj.name
    }

}