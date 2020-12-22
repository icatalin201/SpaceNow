package space.pal.sig.model

import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * SpaceNow
 * Created by Catalin on 12/22/2020
 **/
data class LaunchCore(
        @SerializedName("core")
        val core: String?,
        @SerializedName("flight")
        val flight: Long?,
        @SerializedName("gridfins")
        val gridfins: Boolean?,
        @SerializedName("legs")
        val legs: Boolean?,
        @SerializedName("reused")
        val reused: Boolean?,
        @SerializedName("landing_attempt")
        val landingAttempt: Boolean?,
        @SerializedName("landing_success")
        val landingSuccess: Boolean?,
        @SerializedName("landing_type")
        val landingType: String?,
        @SerializedName("landpad")
        val landpad: String?,
)
