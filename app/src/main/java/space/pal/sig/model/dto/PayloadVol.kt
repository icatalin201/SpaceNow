package space.pal.sig.model.dto

import com.google.gson.annotations.SerializedName

/**
SpaceNow
Created by Catalin on 12/6/2020
 **/
data class PayloadVol(
        @SerializedName("cubic_meters")
        val cubicMeters: Int,
        @SerializedName("cubic_feet")
        val cubicFeet: Int,
)
