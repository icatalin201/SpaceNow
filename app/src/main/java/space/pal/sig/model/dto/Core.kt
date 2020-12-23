package space.pal.sig.model.dto

import com.google.gson.annotations.SerializedName

/**
SpaceNow
Created by Catalin on 12/6/2020
 **/
data class Core(
        @SerializedName("id")
        val id: String,
        @SerializedName("block")
        val block: Long?,
        @SerializedName("serial")
        val serial: String,
        @SerializedName("status")
        val status: String,
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
        @SerializedName("last_update")
        val lastUpdate: String?,
        @SerializedName("launches")
        val launches: List<String>
)
