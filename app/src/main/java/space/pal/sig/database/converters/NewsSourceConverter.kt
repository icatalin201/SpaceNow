package space.pal.sig.database.converters

import androidx.room.TypeConverter
import space.pal.sig.model.entity.NewsSource

/**
 * SpaceNow
 * Created by Catalin on 12/24/2020
 **/
class NewsSourceConverter {

    @TypeConverter
    fun fromString(string: String): NewsSource {
        return NewsSource.fromText(string)
    }

    @TypeConverter
    fun toString(obj: NewsSource): String {
        return obj.text
    }

}