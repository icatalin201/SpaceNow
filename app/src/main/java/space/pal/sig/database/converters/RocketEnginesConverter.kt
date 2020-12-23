package space.pal.sig.database.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import space.pal.sig.model.dto.RocketEngines

/**
 * SpaceNow
 * Created by Catalin on 12/23/2020
 **/
class RocketEnginesConverter {

    private val gson = Gson()

    @TypeConverter
    fun fromString(json: String?): RocketEngines? {
        return if (json == null) {
            null
        } else {
            gson.fromJson(json, RocketEngines::class.java)
        }
    }

    @TypeConverter
    fun toString(obj: RocketEngines?): String? {
        return gson.toJson(obj)
    }

}