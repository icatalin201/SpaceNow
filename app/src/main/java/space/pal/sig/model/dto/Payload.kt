package space.pal.sig.model.dto

import com.google.gson.annotations.SerializedName

/**
SpaceNow
Created by Catalin on 12/6/2020
 **/
data class Payload(
        @SerializedName("id")
        val id: String,
        @SerializedName("name")
        val name: String?,
        @SerializedName("type")
        val type: String?,
        @SerializedName("reused")
        val reused: Boolean,
        @SerializedName("launch")
        val launch: String,
        @SerializedName("customers")
        val customers: List<String>,
        @SerializedName("norad_ids")
        val noradIds: List<Long>,
        @SerializedName("nationalities")
        val nationalities: List<String>,
        @SerializedName("manufacturers")
        val manufacturers: List<String>,
        @SerializedName("mass_kg")
        val massKg: Double?,
        @SerializedName("mass_lbs")
        val massLbs: Double?,
        @SerializedName("orbit")
        val orbit: String?,
        @SerializedName("reference_system")
        val referenceSystem: String?,
        @SerializedName("regime")
        val regime: String?,
        @SerializedName("longitude")
        val longitude: Double?,
        @SerializedName("semi_major_axis_km")
        val semiMajorAxisKm: Double?,
        @SerializedName("eccentricity")
        val eccentricity: Double?,
        @SerializedName("periapsis_km")
        val periapsisKm: Double?,
        @SerializedName("apoapsis_km")
        val apoapsisKm: Double?,
        @SerializedName("inclination_deg")
        val inclinationDeg: Double?,
        @SerializedName("period_min")
        val periodMin: Double?,
        @SerializedName("lifespan_years")
        val lifespanYears: Int?,
        @SerializedName("epoch")
        val epoch: String?
)