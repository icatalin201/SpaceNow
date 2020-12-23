package space.pal.sig.model.dto

import com.google.gson.annotations.SerializedName

/**
SpaceNow
Created by Catalin on 12/6/2020
 **/
data class Thrust(
        @SerializedName("kn")
        val kn: Double,
        @SerializedName("lbf")
        val lbf: Double
)
