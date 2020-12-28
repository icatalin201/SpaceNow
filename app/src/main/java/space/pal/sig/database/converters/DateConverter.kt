package space.pal.sig.database.converters

import androidx.room.TypeConverter
import space.pal.sig.util.DateExtensions.formatDate
import space.pal.sig.util.DateExtensions.parseDateString
import java.util.*

/**
 * SpaceNow
 * Created by Catalin on 7/19/2020
 */
class DateConverter {
    @TypeConverter
    fun fromString(date: String): Date? {
        return parseDateString(date)
    }

    @TypeConverter
    fun toString(date: Date): String {
        return date.formatDate()
    }
}