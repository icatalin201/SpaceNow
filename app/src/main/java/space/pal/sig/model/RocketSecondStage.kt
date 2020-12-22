package space.pal.sig.model

import com.google.gson.annotations.SerializedName

/**
SpaceNow
Created by Catalin on 12/6/2020
 **/
data class RocketSecondStage(
        @SerializedName("reusable")
        val reusable: Boolean,
        @SerializedName("engines")
        val engines: Int,
        @SerializedName("fuel_amount_tons")
        val fuelAmountTons: Double,
        @SerializedName("burn_time_sec")
        val burnTimeSec: Long,
        @SerializedName("thrust")
        val thrust: Thrust,
        @SerializedName("payloads")
        val payloads: RocketPayload
)
