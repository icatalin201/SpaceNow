package space.pal.sig.model.dto

import com.google.gson.annotations.SerializedName

/**
SpaceNow
Created by Catalin on 12/6/2020
 **/
data class Thruster(
        @SerializedName("type")
        val type: String,
        @SerializedName("amount")
        val amount: Double,
        @SerializedName("pods")
        val pods: Int,
        @SerializedName("fuel_1")
        val fuel1: String,
        @SerializedName("fuel_2")
        val fuel2: String,
        @SerializedName("thrust")
        val thrust: Thrust
)
