package space.pal.sig.model

import com.google.gson.annotations.SerializedName

/**
SpaceNow
Created by Catalin on 12/6/2020
 **/
data class OrbitParams(
        @SerializedName("reference_system")
        val referenceSystem: String,
        @SerializedName("regime")
        val regime: String,
        @SerializedName("longitude")
        val longitude: Double?,
        @SerializedName("apoapsis_km")
        val apoapsisKm: Double,
        @SerializedName("periapsis_km")
        val periapsisKm: Double,
        @SerializedName("semi_major_axis_km")
        val semiMajorAxisKm: Double,
        @SerializedName("eccentricity")
        val eccentricity: Double,
        @SerializedName("inclination_deg")
        val inclinationDeg: Double,
        @SerializedName("period_min")
        val periodMin: Int?,
        @SerializedName("lifespan_years")
        val lifespanYears: Int?
)
