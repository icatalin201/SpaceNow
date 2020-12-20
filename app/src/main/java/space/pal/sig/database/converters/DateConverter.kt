package space.pal.sig.database.converters

import androidx.room.TypeConverter
import java.util.*

/**
 * SpaceNow
 * Created by Catalin on 7/19/2020
 */
object DateConverter {
    @TypeConverter
    fun fromTimestamp(time: Long?): Date? {
        return if (time == null) null else Date(time)
    }

    @TypeConverter
    fun toTimestamp(date: Date?): Long? {
        return date?.time
    }
}