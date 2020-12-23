package space.pal.sig.database.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import space.pal.sig.model.dto.Failure
import space.pal.sig.model.dto.LaunchFairing

/**
 * SpaceNow
 * Created by Catalin on 12/23/2020
 **/
class LaunchFairingConverter {

    private val gson = Gson()

    @TypeConverter
    fun fromString(json: String?): LaunchFairing? {
        return if (json == null) {
            null
        } else {
            gson.fromJson(json, LaunchFairing::class.java)
        }
    }

    @TypeConverter
    fun toString(obj: LaunchFairing?): String? {
        return gson.toJson(obj)
    }

    @TypeConverter
    fun fromListString(json: String?): List<LaunchFairing> {
        return if (json == null) {
            listOf()
        } else {
            gson.fromJson(json, Array<LaunchFairing>::class.java).asList()
        }
    }

    @TypeConverter
    fun toListString(list: List<LaunchFairing>): String {
        return gson.toJson(list)
    }

}