package space.pal.sig.model

import com.google.gson.annotations.SerializedName

/**
SpaceNow
Created by Catalin on 12/6/2020
 **/
data class Payload(
        @SerializedName("payload_id")
        val id: String,
        @SerializedName("norad_id")
        val noradId: MutableList<Long>,
        @SerializedName("reused")
        val reused: Boolean,
        @SerializedName("customers")
        val customers: MutableList<String>,
        @SerializedName("nationality")
        val nationality: String,
        @SerializedName("manufacturer")
        val manufacturer: String,
        @SerializedName("payload_type")
        val type: String,
        @SerializedName("payload_mass_kg")
        val massKg: Double,
        @SerializedName("payload_mass_lbs")
        val massLbs: Double,
        @SerializedName("orbit")
        val orbit: String,
        @SerializedName("orbit_params")
        val orbitParams: OrbitParams
)