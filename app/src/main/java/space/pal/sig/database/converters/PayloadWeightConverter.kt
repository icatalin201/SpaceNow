package space.pal.sig.database.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import space.pal.sig.model.dto.PayloadWeight

/**
 * SpaceNow
 * Created by Catalin on 12/23/2020
 **/
class PayloadWeightConverter {

    private val gson = Gson()

    @TypeConverter
    fun fromString(json: String?): PayloadWeight? {
        return if (json == null) {
            null
        } else {
            gson.fromJson(json, PayloadWeight::class.java)
        }
    }

    @TypeConverter
    fun toString(obj: PayloadWeight?): String? {
        return gson.toJson(obj)
    }

    @TypeConverter
    fun fromListString(json: String?): List<PayloadWeight> {
        return if (json == null) {
            listOf()
        } else {
            gson.fromJson(json, Array<PayloadWeight>::class.java).asList()
        }
    }

    @TypeConverter
    fun toListString(list: List<PayloadWeight>): String {
        return gson.toJson(list)
    }

}