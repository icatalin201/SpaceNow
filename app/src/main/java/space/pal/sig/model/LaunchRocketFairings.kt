package space.pal.sig.model

import com.google.gson.annotations.SerializedName

/**
SpaceNow
Created by Catalin on 12/6/2020
 **/
data class LaunchRocketFairings(
        @SerializedName("reused")
        val reused: Boolean,
        @SerializedName("recovery_attempt")
        val recoveryAttempts: Boolean,
        @SerializedName("recovered")
        val recovered: Boolean,
        @SerializedName("ship")
        val ship: Ship?
)
