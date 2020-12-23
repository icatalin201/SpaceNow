package space.pal.sig.model.dto

import com.google.gson.annotations.SerializedName
import java.util.*

/**
SpaceNow
Created by Catalin on 12/6/2020
 **/
data class LandingPad(
        @SerializedName("id")
        val id: String,
        @SerializedName("name")
        val name: String?,
        @SerializedName("full_name")
        val fullName: String?,
        @SerializedName("status")
        val status: String,
        @SerializedName("type")
        val type: String?,
        @SerializedName("locality")
        val locality: String?,
        @SerializedName("region")
        val region: String?,
        @SerializedName("latitude")
        val latitude: Double?,
        @SerializedName("longitude")
        val longitude: Double?,
        @SerializedName("landing_attempts")
        val landingAttempts: Int,
        @SerializedName("landing_successes")
        val landingSuccesses: Int,
        @SerializedName("wikipedia")
        val wikipedia: String?,
        @SerializedName("details")
        val details: String?,
        @SerializedName("launches")
        val launches: List<String>
)
