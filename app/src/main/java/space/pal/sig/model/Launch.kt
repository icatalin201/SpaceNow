package space.pal.sig.model

import com.google.gson.annotations.SerializedName

/**
SpaceNow
Created by Catalin on 12/6/2020
 **/
data class Launch(
        @SerializedName("flight_number")
        val flightNumber: Long,
        @SerializedName("mission_name")
        val missionName: String,
        @SerializedName("mission_id")
        val missionId: MutableList<String>,
        @SerializedName("launch_year")
        val launchYear: String,
        @SerializedName("launch_date_unix")
        val launchDateUnix: Long,
        @SerializedName("launch_date_utc")
        val launchDateUtc: String,
        @SerializedName("launch_date_local")
        val launchDateLocal: String,
        @SerializedName("is_tentative")
        val isTentative: Boolean,
        @SerializedName("tentative_max_precision")
        val tentativeMaxPrecision: String,
        @SerializedName("tbd")
        val tbd: Boolean,
        @SerializedName("launch_window")
        val launchWindow: Int,
        @SerializedName("launch_success")
        val launchSuccess: Boolean,
        @SerializedName("details")
        val details: String,
        @SerializedName("upcoming")
        val upcoming: Boolean,
        @SerializedName("static_fire_date_utc")
        val staticFireDateUtc: String,
        @SerializedName("static_fire_date_unix")
        val staticFireDateUnix: Long,
        @SerializedName("ships")
        val ships: MutableList<String>,
        @SerializedName("launch_site")
        val launchSite: LaunchSite,
        @SerializedName("rocket")
        val rocket: LaunchRocket
)
