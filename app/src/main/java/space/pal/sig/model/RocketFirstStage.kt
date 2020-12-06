package space.pal.sig.model

import com.google.gson.annotations.SerializedName

/**
SpaceNow
Created by Catalin on 12/6/2020
 **/
data class RocketFirstStage(
        @SerializedName("reusable")
        val reusable: Boolean,
        @SerializedName("engines")
        val engines: Int,
        @SerializedName("fuel_amount_tons")
        val fuelAmountTons: Double,
        @SerializedName("burn_time_sec")
        val burnTimeSec: Long,
        @SerializedName("thrust_sea_level")
        val thrustSeaLevel: Thrust,
        @SerializedName("thrust_vacuum")
        val thrustVacuum: Thrust
)
