package space.pal.sig.database.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import space.pal.sig.model.dto.RocketFirstStage

/**
 * SpaceNow
 * Created by Catalin on 12/23/2020
 **/
class RocketFirstStageConverter {

    private val gson = Gson()

    @TypeConverter
    fun fromString(json: String?): RocketFirstStage? {
        return if (json == null) {
            null
        } else {
            gson.fromJson(json, RocketFirstStage::class.java)
        }
    }

    @TypeConverter
    fun toString(obj: RocketFirstStage?): String? {
        return gson.toJson(obj)
    }

}