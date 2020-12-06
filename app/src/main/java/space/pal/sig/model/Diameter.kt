package space.pal.sig.model

import com.google.gson.annotations.SerializedName

/**
SpaceNow
Created by Catalin on 12/6/2020
 **/
data class Diameter(
        @SerializedName("meters")
        val meters: Double,
        @SerializedName("feet")
        val feet: Double
)
