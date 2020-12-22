package space.pal.sig.model

import com.google.gson.annotations.SerializedName

/**
 * SpaceNow
 * Created by Catalin on 12/22/2020
 **/
data class Failure(
        @SerializedName("time")
        val time: Long,
        @SerializedName("altitude")
        val altitude: Long,
        @SerializedName("reason")
        val reason: String
)