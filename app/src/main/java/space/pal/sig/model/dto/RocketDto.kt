package space.pal.sig.model.dto

import com.google.gson.annotations.SerializedName
import space.pal.sig.model.entity.Rocket

/**
SpaceNow
Created by Catalin on 12/6/2020
 **/
data class RocketDto(
        @SerializedName("id")
        val id: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("type")
        val type: String,
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
        @SerializedName("height")
        val height: Diameter,
        @SerializedName("diameter")
        val diameter: Diameter,
        @SerializedName("mass")
        val mass: PayloadMass,
        @SerializedName("payload_weights")
        val payloadWeights: List<PayloadWeight>,
        @SerializedName("first_stage")
        val firstStage: RocketFirstStage,
        @SerializedName("second_stage")
        val secondStage: RocketSecondStage,
        @SerializedName("engines")
        val engines: RocketEngines,
        @SerializedName("landing_legs")
        val landingLegs: RocketLandingLegs,
        @SerializedName("wikipedia")
        val wikipedia: String,
        @SerializedName("description")
        val description: String,
        @SerializedName("flickr_images")
        val images: List<String>
) {
    fun toRocket(): Rocket {
        return Rocket(
                id, name, type, active, stages,
                boosters, costPerLaunch, successRatePct,
                firstFlight, country, company, height,
                diameter, mass, payloadWeights, firstStage,
                secondStage, engines, landingLegs, wikipedia,
                description, images
        )
    }
}
