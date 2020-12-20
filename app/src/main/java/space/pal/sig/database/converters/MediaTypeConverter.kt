package space.pal.sig.database.converters

import androidx.room.TypeConverter
import space.pal.sig.old.model.MediaType

/**
 * SpaceNow
 * Created by Catalin on 7/24/2020
 */
object MediaTypeConverter {
    @TypeConverter
    fun fromString(type: String?): MediaType? {
        return if (type == null) null else MediaType.valueOf(type)
    }

    @TypeConverter
    fun toString(type: MediaType?): String? {
        return type?.name
    }
}