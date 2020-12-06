package space.pal.sig.model

import com.google.gson.annotations.SerializedName

/**
SpaceNow
Created by Catalin on 12/6/2020
 **/
data class AstronomyPictureOfTheDay(
        @SerializedName(value = "date", alternate = ["date_created"])
        val date: String,
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
)
