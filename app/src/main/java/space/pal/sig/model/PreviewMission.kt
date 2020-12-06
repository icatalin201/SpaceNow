package space.pal.sig.model

import com.google.gson.annotations.SerializedName

/**
SpaceNow
Created by Catalin on 12/6/2020
 **/
data class PreviewMission(
        @SerializedName("name")
        val name: String,
        @SerializedName("flight")
        val flight: Int
)
