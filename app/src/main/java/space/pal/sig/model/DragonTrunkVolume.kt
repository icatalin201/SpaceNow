package space.pal.sig.model

import com.google.gson.annotations.SerializedName

/**
SpaceNow
Created by Catalin on 12/6/2020
 **/
data class DragonTrunkVolume(
        @SerializedName("cubic_meters")
        val cubicMeters: Int,
        @SerializedName("cubic_feet")
        val cubicFeet: Int
)
