package space.pal.sig.model.dto

import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * SpaceNow
 * Created by Catalin on 12/22/2020
 **/
data class LaunchFairing(
        @SerializedName("reused")
        val reused: Boolean?,
        @SerializedName("recovery_attempt")
        val recoveryAttempt: Boolean?,
        @SerializedName("recovered")
        val recovered: Boolean?,
        @SerializedName("ships")
        val ships: List<String>
)
