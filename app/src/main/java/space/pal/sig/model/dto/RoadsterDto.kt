package space.pal.sig.model.dto

import com.google.gson.annotations.SerializedName
import space.pal.sig.model.entity.Roadster

/**
SpaceNow
Created by Catalin on 12/6/2020
 **/
data class RoadsterDto(
        @SerializedName("name")
        val name: String,
        @SerializedName("launch_date_unix")
        val launchDateUnix: Long,
        @SerializedName("launch_date_utc")
        val launchDateUtc: String,
        @SerializedName("launch_mass_kg")
        val launchMassKg: Double,
        @SerializedName("launch_mass_lbs")
        val launchMassLbs: Double,
        @SerializedName("norad_id")
        val noradId: Double,
        @SerializedName("epoch_jd")
        val epochJd: Double,
        @SerializedName("orbit_type")
        val orbitType: String,
        @SerializedName("apoapsis_au")
        val apoapsisAu: Double,
        @SerializedName("periapsis_au")
        val periapsisAu: Double,
        @SerializedName("semi_major_axis_au")
        val semiMajorAxisAu: Double,
        @SerializedName("eccentricity")
        val eccentricity: Double,
        @SerializedName("inclination")
        val inclination: Double,
        @SerializedName("longitude")
        val longitude: Double,
        @SerializedName("periapsis_arg")
        val periapsisArg: Double,
        @SerializedName("period_days")
        val periodDays: Double,
        @SerializedName("speed_kph")
        val speedKph: Double,
        @SerializedName("speed_mph")
        val speedMph: Double,
        @SerializedName("earth_distance_km")
        val earthDistanceKm: Double,
        @SerializedName("earth_distance_mi")
        val earthDistanceMi: Double,
        @SerializedName("mars_distance_km")
        val marsDistanceKm: Double,
        @SerializedName("mars_distance_mi")
        val marsDistanceMi: Double,
        @SerializedName("wikipedia")
        val wikipedia: String,
        @SerializedName("details")
        val details: String,
        @SerializedName("video")
        val video: String,
        @SerializedName("flickr_images")
        val images: List<String>
) {
    fun toRoadster(): Roadster {
        return Roadster(
                name, launchDateUnix, launchDateUtc, launchMassKg,
                launchMassLbs, noradId, epochJd, orbitType,
                apoapsisAu, periapsisAu, semiMajorAxisAu,
                eccentricity, inclination, longitude, periapsisArg,
                periodDays, speedKph, speedMph, earthDistanceKm,
                earthDistanceMi, marsDistanceKm, marsDistanceMi,
                wikipedia, details, video, images
        )
    }
}
