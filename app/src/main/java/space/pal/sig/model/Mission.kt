package space.pal.sig.model

import com.google.gson.annotations.SerializedName

/**
SpaceNow
Created by Catalin on 12/6/2020
 **/
data class Mission(
        @SerializedName("mission_name")
        val name: String,
        @SerializedName("mission_id")
        val id: String,
        @SerializedName("manufacturers")
        val manufacturers: MutableList<String>,
        @SerializedName("payload_ids")
        val payloadIds: MutableList<String>,
        @SerializedName("wikipedia")
        val wikipedia: String,
        @SerializedName("website")
        val website: String,
        @SerializedName("twitter")
        val twitter: String,
        @SerializedName("description")
        val description: String
)
