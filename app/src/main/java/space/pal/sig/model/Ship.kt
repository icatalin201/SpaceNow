package space.pal.sig.model

import com.google.gson.annotations.SerializedName

/**
SpaceNow
Created by Catalin on 12/6/2020
 **/
data class Ship(
        @SerializedName("ship_id")
        val id: String,
        @SerializedName("ship_name")
        val name: String,
        @SerializedName("ship_model")
        val model: String?,
        @SerializedName("ship_type")
        val type: String,
        @SerializedName("roles")
        val roles: MutableList<String>,
        @SerializedName("active")
        val active: Boolean,
        @SerializedName("imo")
        val imo: Long,
        @SerializedName("mmsi")
        val mmsi: Long,
        @SerializedName("abs")
        val abs: Long,
        @SerializedName("class")
        val shipClass: Long,
        @SerializedName("weight_lbs")
        val weightLbs: Double,
        @SerializedName("weight_kg")
        val weightKg: Double,
        @SerializedName("year_built")
        val yearBuilt: Int,
        @SerializedName("home_port")
        val homePort: String,
        @SerializedName("status")
        val status: String,
        @SerializedName("speed_kn")
        val speedKn: Double,
        @SerializedName("course_deg")
        val courseDeg: Double?,
        @SerializedName("attempted_landings")
        val attemptedLandings: Int,
        @SerializedName("successful_landings")
        val successfulLandings: Int,
        @SerializedName("missions")
        val missions: MutableList<PreviewMission>,
        @SerializedName("url")
        val url: String,
        @SerializedName("image")
        val image: String,
        @SerializedName("position")
        val position: Position
)
