package space.pal.sig.database.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import space.pal.sig.model.dto.RocketLandingLegs

/**
 * SpaceNow
 * Created by Catalin on 12/23/2020
 **/
class RocketLandingLegsConverter {

    private val gson = Gson()

    @TypeConverter
    fun fromString(json: String?): RocketLandingLegs? {
        return if (json == null) {
            null
        } else {
            gson.fromJson(json, RocketLandingLegs::class.java)
        }
    }

    @TypeConverter
    fun toString(obj: RocketLandingLegs?): String? {
        return gson.toJson(obj)
    }

}