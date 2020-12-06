package space.pal.sig.model

import com.google.gson.annotations.SerializedName

/**
SpaceNow
Created by Catalin on 12/6/2020
 **/
data class Capsule(
        @SerializedName("capsule_id")
        val id: String,
        @SerializedName("capsule_serial")
        val serial: String,
        @SerializedName("status")
        val status: String,
        @SerializedName("original_launch")
        val originalLaunch: String,
        @SerializedName("original_launch_unix")
        val originalLaunchUnix: Long,
        @SerializedName("landings")
        val landings: Int,
        @SerializedName("type")
        val type: String,
        @SerializedName("details")
        val details: String,
        @SerializedName("reuse_count")
        val reuseCount: Int,
        @SerializedName("missions")
        val missions: MutableList<PreviewMission>
)
