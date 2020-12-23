package space.pal.sig.model.dto

import com.google.gson.annotations.SerializedName

/**
SpaceNow
Created by Catalin on 12/6/2020
 **/
data class PayloadMass(
        @SerializedName("kg")
        val kg: Double,
        @SerializedName("lb")
        val lb: Double
)