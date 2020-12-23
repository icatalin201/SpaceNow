package space.pal.sig.model.dto

import com.google.gson.annotations.SerializedName

/**
SpaceNow
Created by Catalin on 12/6/2020
 **/
data class Ship(
        @SerializedName("id")
        val id: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("legacy_id")
        val legacyId: String?,
        @SerializedName("model")
        val model: String?,
        @SerializedName("type")
        val type: String?,
        @SerializedName("roles")
        val roles: MutableList<String>,
        @SerializedName("active")
        val active: Boolean,
        @SerializedName("imo")
        val imo: Long?,
        @SerializedName("mmsi")
        val mmsi: Long?,
        @SerializedName("abs")
        val abs: Long?,
        @SerializedName("class")
        val shipClass: Long?,
        @SerializedName("mass_lbs")
        val massLbs: Double?,
        @SerializedName("mass_kg")
        val massKg: Double?,
        @SerializedName("year_built")
        val yearBuilt: Int?,
        @SerializedName("home_port")
        val homePort: String?,
        @SerializedName("status")
        val status: String?,
        @SerializedName("speed_kn")
        val speedKn: Double?,
        @SerializedName("course_deg")
        val courseDeg: Double?,
        @SerializedName("latitude")
        val latitude: Double?,
        @SerializedName("longitude")
        val longitude: Double?,
        @SerializedName("image")
        val image: String?,
        @SerializedName("link")
        val link: String?,
        @SerializedName("last_ais_update")
        val lastAisUpdate: String?,
        @SerializedName("launches")
        val launches: List<String>
)
