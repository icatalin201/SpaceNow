package space.pal.sig.database.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import space.pal.sig.model.dto.DataLinks

/**
 * SpaceNow
 * Created by Catalin on 12/23/2020
 **/
class DataLinkConverter {

    private val gson = Gson()

    @TypeConverter
    fun fromString(json: String?): DataLinks? {
        return if (json == null) {
            null
        } else {
            gson.fromJson(json, DataLinks::class.java)
        }
    }

    @TypeConverter
    fun toString(obj: DataLinks?): String? {
        return gson.toJson(obj)
    }

}