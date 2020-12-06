package space.pal.sig.model

import com.google.gson.annotations.SerializedName

/**
SpaceNow
Created by Catalin on 12/6/2020
 **/
data class Location(
        @SerializedName("name")
        val name: String,
        @SerializedName("region")
        val region: String,
        @SerializedName("latitude")
        val latitude: Double,
        @SerializedName("longitude")
        val longitude: Double,
)
