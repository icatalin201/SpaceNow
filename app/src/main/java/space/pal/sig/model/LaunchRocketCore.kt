package space.pal.sig.model

import com.google.gson.annotations.SerializedName

/**
SpaceNow
Created by Catalin on 12/6/2020
 **/
data class LaunchRocketCore(
        @SerializedName("serial")
        val serial: String,
        @SerializedName("flight")
        val flight: Long,
        @SerializedName("block")
        val block: String?,
        @SerializedName("gridfins")
        val gridfins: Boolean,
        @SerializedName("legs")
        val legs: Boolean,
        @SerializedName("reused")
        val reused: Boolean,
        @SerializedName("land_success")
        val landSuccess: Boolean?,
        @SerializedName("landing_intent")
        val landingIntent: Boolean,
        @SerializedName("landing_type")
        val landingType: String?,
        @SerializedName("landing_vehicle")
        val landingVehicle: String?
)
