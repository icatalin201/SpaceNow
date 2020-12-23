package space.pal.sig.model.dto

import com.google.gson.annotations.SerializedName

/**
SpaceNow
Created by Catalin on 12/6/2020
 **/
data class RocketEngines(
        @SerializedName("number")
        val number: Int,
        @SerializedName("type")
        val type: String,
        @SerializedName("version")
        val version: String,
        @SerializedName("layout")
        val layout: String,
        @SerializedName("engine_loss_max")
        val engineLossMax: Int,
        @SerializedName("propellant_1")
        val propellant1: String,
        @SerializedName("propellant_2")
        val propellant2: String,
        @SerializedName("thrust_to_weight")
        val thrustToWeight: Double,
        @SerializedName("thrust_sea_level")
        val thrustSeaLevel: Thrust,
        @SerializedName("thrust_vacuum")
        val thrustVacuum: Thrust
)
