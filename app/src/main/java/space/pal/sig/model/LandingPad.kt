package space.pal.sig.model

import com.google.gson.annotations.SerializedName

/**
SpaceNow
Created by Catalin on 12/6/2020
 **/
data class LandingPad(
        @SerializedName("id")
        val id: String,
        @SerializedName("full_name")
        val fullName: String,
        @SerializedName("status")
        val status: String,
        @SerializedName("landing_type")
        val landingType: String,
        @SerializedName("attempted_landings")
        val attemptedLandings: Int,
        @SerializedName("successful_landings")
        val successfulLandings: Int,
        @SerializedName("wikipedia")
        val wikipedia: String,
        @SerializedName("details")
        val details: String,
        @SerializedName("location")
        val location: Location
)
