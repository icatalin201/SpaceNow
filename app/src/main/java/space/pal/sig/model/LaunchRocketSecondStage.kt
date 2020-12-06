package space.pal.sig.model

import com.google.gson.annotations.SerializedName

/**
SpaceNow
Created by Catalin on 12/6/2020
 **/
data class LaunchRocketSecondStage(
        @SerializedName("block")
        val block: Int,
        @SerializedName("payloads")
        val payloads: MutableList<Payload>
)
