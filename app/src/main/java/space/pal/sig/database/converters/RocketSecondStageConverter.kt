package space.pal.sig.database.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import space.pal.sig.model.dto.RocketSecondStage

/**
 * SpaceNow
 * Created by Catalin on 12/23/2020
 **/
class RocketSecondStageConverter {

    private val gson = Gson()

    @TypeConverter
    fun fromString(json: String?): RocketSecondStage? {
        return if (json == null) {
            null
        } else {
            gson.fromJson(json, RocketSecondStage::class.java)
        }
    }

    @TypeConverter
    fun toString(obj: RocketSecondStage?): String? {
        return gson.toJson(obj)
    }

}