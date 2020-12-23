package space.pal.sig.database.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import space.pal.sig.model.dto.Diameter

/**
 * SpaceNow
 * Created by Catalin on 12/23/2020
 **/
class DiameterConverter {

    private val gson = Gson()

    @TypeConverter
    fun fromString(json: String?): Diameter? {
        return if (json == null) {
            null
        } else {
            gson.fromJson(json, Diameter::class.java)
        }
    }

    @TypeConverter
    fun toString(obj: Diameter?): String? {
        return gson.toJson(obj)
    }

}