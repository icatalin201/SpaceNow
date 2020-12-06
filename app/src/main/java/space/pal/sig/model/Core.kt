package space.pal.sig.model

import com.google.gson.annotations.SerializedName

/**
SpaceNow
Created by Catalin on 12/6/2020
 **/
data class Core(
        @SerializedName("block")
        val block: Int?,
        @SerializedName("core_serial")
        val serial: String,
        @SerializedName("status")
        val status: String,
        @SerializedName("original_launch")
        val originalLaunch: String,
        @SerializedName("original_launch_unix")
        val originalLaunchUnix: Long,
        @SerializedName("missions")
        val missions: MutableList<PreviewMission>,
        @SerializedName("details")
        val details: String,
        @SerializedName("reuse_count")
        val reuseCount: Int,
        @SerializedName("rtls_attempts")
        val rtlsAttempts: Int,
        @SerializedName("rtls_landings")
        val rtlsLandings: Int,
        @SerializedName("asds_attempts")
        val asdsAttempts: Int,
        @SerializedName("asds_landings")
        val asdsLandings: Int,
        @SerializedName("water_landing")
        val waterLanding: Boolean,
)
