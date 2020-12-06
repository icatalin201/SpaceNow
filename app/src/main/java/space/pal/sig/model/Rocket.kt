package space.pal.sig.model

import com.google.gson.annotations.SerializedName

/**
SpaceNow
Created by Catalin on 12/6/2020
 **/
data class Rocket(
        @SerializedName("id")
        val id: Long,
        @SerializedName("active")
        val active: Boolean,
        @SerializedName("stages")
        val stages: Int,
        @SerializedName("boosters")
        val boosters: Int,
        @SerializedName("cost_per_launch")
        val costPerLaunch: Double,
        @SerializedName("success_rate_pct")
        val successRatePct: Int,
        @SerializedName("first_flight")
        val firstFlight: String,
        @SerializedName("country")
        val country: String,
        @SerializedName("company")
        val company: String,
        @SerializedName("wikipedia")
        val wikipedia: String,
        @SerializedName("description")
        val description: String,
        @SerializedName("rocket_id")
        val rocketId: String,
        @SerializedName("rocket_name")
        val name: String,
        @SerializedName("rocket_type")
        val type: String,
        @SerializedName("payload_weights")
        val payloadWeights: MutableList<PayloadWeight>,
        @SerializedName("diameter")
        val diameter: Diameter,
        @SerializedName("height")
        val height: Diameter,
        @SerializedName("mass")
        val mass: PayloadMass,
        @SerializedName("landing_legs")
        val landingLegs: RocketLandingLegs,
        @SerializedName("engines")
        val engines: RocketEngines,
        @SerializedName("first_stage")
        val firstStage: RocketFirstStage,
        @SerializedName("second_stage")
        val secondStage: RocketSecondStage
)
