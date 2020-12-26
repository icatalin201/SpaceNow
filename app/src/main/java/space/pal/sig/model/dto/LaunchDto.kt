package space.pal.sig.model.dto

import com.google.gson.annotations.SerializedName
import space.pal.sig.model.entity.Launch

/**
SpaceNow
Created by Catalin on 12/6/2020
 **/
data class LaunchDto(
        @SerializedName("id")
        val id: String,
        @SerializedName("flight_number")
        val flightNumber: Long,
        @SerializedName("name")
        val name: String,
        @SerializedName("date_utc")
        val dateUtc: String,
        @SerializedName("date_unix")
        val dateUnix: Long,
        @SerializedName("date_local")
        val dateLocal: String,
        @SerializedName("static_fire_date_utc")
        val staticFireDateUtc: String?,
        @SerializedName("static_fire_date_unix")
        val staticFireDateUnix: Long?,
        @SerializedName("tbd")
        val tbd: Boolean,
        @SerializedName("net")
        val net: Boolean,
        @SerializedName("window")
        val window: Long?,
        @SerializedName("rocket")
        val rocket: String,
        @SerializedName("success")
        val success: Boolean?,
        @SerializedName("failures")
        val failures: List<Failure>,
        @SerializedName("upcoming")
        val upcoming: Boolean,
        @SerializedName("details")
        val details: String?,
        @SerializedName("crew")
        val crew: List<String>,
        @SerializedName("ships")
        val ships: List<String>,
        @SerializedName("capsules")
        val capsules: List<String>,
        @SerializedName("payloads")
        val payloads: List<String>,
        @SerializedName("launchpad")
        val launchpad: String?,
        @SerializedName("links")
        val links: DataLinks,
        @SerializedName("auto_update")
        val autoUpdate: Boolean,
        @SerializedName("cores")
        val cores: List<LaunchCore>,
        @SerializedName("fairings")
        val fairings: LaunchFairing?
) {
    fun toLaunch(): Launch {
        return Launch(
                id, flightNumber, name, dateUtc, dateUnix * 1000,
                dateLocal, staticFireDateUtc, staticFireDateUnix,
                tbd, net, window, rocket, success, failures,
                upcoming, details, crew, ships, capsules, payloads,
                launchpad, links, autoUpdate, cores, fairings
        )
    }
}
