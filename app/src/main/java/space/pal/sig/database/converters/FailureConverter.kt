package space.pal.sig.database.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import space.pal.sig.model.dto.Failure

/**
 * SpaceNow
 * Created by Catalin on 12/23/2020
 **/
class FailureConverter {

    private val gson = Gson()

    @TypeConverter
    fun fromString(json: String?): Failure? {
        return if (json == null) {
            null
        } else {
            gson.fromJson(json, Failure::class.java)
        }
    }

    @TypeConverter
    fun fromListString(json: String?): List<Failure> {
        return if (json == null) {
            listOf()
        } else {
            gson.fromJson(json, Array<Failure>::class.java).asList()
        }
    }

    @TypeConverter
    fun toListString(list: List<Failure>): String {
        return gson.toJson(list)
    }

    @TypeConverter
    fun toString(obj: Failure?): String? {
        return gson.toJson(obj)
    }
}
