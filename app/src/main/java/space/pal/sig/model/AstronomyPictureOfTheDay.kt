package space.pal.sig.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.*

/**
SpaceNow
Created by Catalin on 12/6/2020
 **/
@Entity(tableName = "astronomy_pictures_of_the_day")
data class AstronomyPictureOfTheDay(
        @PrimaryKey(autoGenerate = false)
        @SerializedName(value = "date", alternate = ["date_created"])
        val date: Date,
        @SerializedName(value = "explanation", alternate = ["description"])
        val explanation: String,
        @SerializedName(value = "url", alternate = ["image"])
        val url: String,
        @SerializedName("title")
        val title: String,
        @SerializedName(value = "copyright", alternate = ["photographer"])
        val copyright: String,
        @SerializedName("service_version")
        val serviceVersion: String,
        @SerializedName("media_type")
        val mediaType: String,
        @SerializedName(value = "hdurl", alternate = ["hd_image"])
        val hdUrl: String
) {
    companion object {
        const val IMAGE = "image"
        const val VIDEO = "video"
    }
}
