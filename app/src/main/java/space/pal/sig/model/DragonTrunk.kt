package space.pal.sig.model

import com.google.gson.annotations.SerializedName

/**
SpaceNow
Created by Catalin on 12/6/2020
 **/
data class DragonTrunk(
        @SerializedName("trunk_volume")
        val trunkVolume: DragonTrunkVolume,
        @SerializedName("cargo")
        val cargo: DragonTrunkCargo
)
