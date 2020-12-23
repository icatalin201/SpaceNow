package space.pal.sig.database.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import space.pal.sig.model.dto.LaunchCore

/**
 * SpaceNow
 * Created by Catalin on 12/23/2020
 **/
class LaunchCoreConverter {

    private val gson = Gson()

    @TypeConverter
    fun fromString(json: String?): LaunchCore? {
        return if (json == null) {
            null
        } else {
            gson.fromJson(json, LaunchCore::class.java)
        }
    }

    @TypeConverter
    fun toString(obj: LaunchCore?): String? {
        return gson.toJson(obj)
    }

    @TypeConverter
    fun fromListString(json: String?): List<LaunchCore> {
        return if (json == null) {
            listOf()
        } else {
            gson.fromJson(json, Array<LaunchCore>::class.java).asList()
        }
    }

    @TypeConverter
    fun toListString(list: List<LaunchCore>): String {
        return gson.toJson(list)
    }

}