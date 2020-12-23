package space.pal.sig.model.dto

import com.google.gson.annotations.SerializedName

/**
SpaceNow
Created by Catalin on 12/6/2020
 **/
data class RocketPayload(
        @SerializedName("option_1")
        val option1: String,
        @SerializedName("composite_fairing")
        val compositeFairing: RocketPayloadCompositeFairing
)
