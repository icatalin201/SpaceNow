package space.pal.sig.model

import com.google.gson.annotations.SerializedName

/**
SpaceNow
Created by Catalin on 12/6/2020
 **/
data class RocketPayloadCompositeFairing(
        @SerializedName("height")
        val height: Diameter,
        @SerializedName("diameter")
        val diameter: Diameter
)