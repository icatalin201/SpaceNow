package space.pal.sig.database.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import space.pal.sig.model.dto.PayloadMass

/**
 * SpaceNow
 * Created by Catalin on 12/23/2020
 **/
class PayloadMassConverter {

    private val gson = Gson()

    @TypeConverter
    fun fromString(json: String?): PayloadMass? {
        return if (json == null) {
            null
        } else {
            gson.fromJson(json, PayloadMass::class.java)
        }
    }

    @TypeConverter
    fun toString(obj: PayloadMass?): String? {
        return gson.toJson(obj)
    }

}