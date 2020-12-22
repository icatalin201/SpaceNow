package space.pal.sig.model

import com.google.gson.annotations.SerializedName

/**
SpaceNow
Created by Catalin on 12/6/2020
 **/
data class HeatShield(
        @SerializedName("material")
        val material: String,
        @SerializedName("size_meters")
        val sizeMeters: Double,
        @SerializedName("temp_degrees")
        val tempDegrees: Double?,
        @SerializedName("dev_partner")
        val devPartner: String?
)
