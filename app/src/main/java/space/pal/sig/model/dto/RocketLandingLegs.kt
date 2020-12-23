package space.pal.sig.model.dto

import com.google.gson.annotations.SerializedName

/**
SpaceNow
Created by Catalin on 12/6/2020
 **/
data class RocketLandingLegs(
        @SerializedName("number")
        val number: Int,
        @SerializedName("material")
        val material: String
)
