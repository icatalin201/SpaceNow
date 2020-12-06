package space.pal.sig.model

import com.google.gson.annotations.SerializedName

/**
SpaceNow
Created by Catalin on 12/6/2020
 **/
data class LaunchPad(
        @SerializedName("id")
        val id: String,
        @SerializedName("status")
        val status: String,
        @SerializedName("location")
        val location: Location,
        @SerializedName("vehicles_launched")
        val vehiclesLaunched: MutableList<String>,
        @SerializedName("site_id")
        val siteId: String,
        @SerializedName("site_name_long")
        val siteNameLong: String,
        @SerializedName("attempted_launches")
        val attemptedLaunches: Int,
        @SerializedName("successful_launches")
        val successfulLaunches: Int,
        @SerializedName("wikipedia")
        val wikipedia: String,
        @SerializedName("details")
        val details: String,
)
