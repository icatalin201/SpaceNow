package space.pal.sig.model

import com.google.gson.annotations.SerializedName

/**
SpaceNow
Created by Catalin on 12/6/2020
 **/
data class DragonTrunkCargo(
        @SerializedName("solar_array")
        val solarArray: Int,
        @SerializedName("unpressurized_cargo")
        val unpressurizedCargo: Boolean
)
