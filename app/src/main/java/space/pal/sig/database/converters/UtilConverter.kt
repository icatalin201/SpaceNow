package space.pal.sig.database.converters

import androidx.room.TypeConverter
import com.google.gson.Gson

/**
 * SpaceNow
 * Created by Catalin on 12/23/2020
 **/
class UtilConverter {

    private val gson = Gson()

    @TypeConverter
    fun toListString(list: List<String>): String {
        return gson.toJson(list)
    }

    @TypeConverter
    fun fromListString(json: String?): List<String> {
        return if (json == null) {
            listOf()
        } else {
            gson.fromJson(json, Array<String>::class.java).asList()
        }
    }

}