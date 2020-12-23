package space.pal.sig.model.dto

import com.google.gson.annotations.SerializedName

/**
 * SpaceNow
 * Created by Catalin on 12/22/2020
 **/
data class LinkReddit(
        @SerializedName("campaign")
        val campaign: String?,
        @SerializedName("launch")
        val launch: String?,
        @SerializedName("media")
        val media: String?,
        @SerializedName("recovery")
        val recovery: String?
)
