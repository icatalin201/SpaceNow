package space.pal.sig.model.dto

import com.google.gson.annotations.SerializedName

/**
SpaceNow
Created by Catalin on 12/6/2020
 **/
data class Dragon(
        @SerializedName("id")
        val id: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("type")
        val type: String,
        @SerializedName("active")
        val active: Boolean,
        @SerializedName("crew_capacity")
        val crewCapacity: Int,
        @SerializedName("sidewall_angle_deg")
        val sidewallAngleDeg: Double,
        @SerializedName("orbit_duration_yr")
        val orbitDurationYr: Double,
        @SerializedName("dry_mass_kg")
        val dryMassKg: Double,
        @SerializedName("dry_mass_lb")
        val dryMassLb: Double,
        @SerializedName("first_flight")
        val firstFlight: String?,
        @SerializedName("heat_shield")
        val heatShield: HeatShield,
        @SerializedName("launch_payload_mass")
        val launchPayloadMass: PayloadMass,
        @SerializedName("return_payload_mass")
        val returnPayloadMass: PayloadMass,
        @SerializedName("launch_payload_vol")
        val launchPayloadVol: PayloadVol,
        @SerializedName("return_payload_vol")
        val returnPayloadVol: PayloadVol,
        @SerializedName("diameter")
        val diameter: Diameter,
        @SerializedName("trunk")
        val trunk: DragonTrunk,
        @SerializedName("wikipedia")
        val wikipedia: String?,
        @SerializedName("description")
        val description: String?,
        @SerializedName("flickr_images")
        val images: List<String>,
        @SerializedName("thrusters")
        val thrusters: MutableList<Thruster>
)
