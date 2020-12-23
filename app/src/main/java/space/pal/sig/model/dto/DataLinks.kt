package space.pal.sig.model.dto

import com.google.gson.annotations.SerializedName

/**
SpaceNow
Created by Catalin on 12/6/2020
 **/
data class DataLinks(
        @SerializedName("patch")
        val patch: LinkPatch,
        @SerializedName("reddit")
        val reddit: LinkReddit,
        @SerializedName("flickr")
        val flickr: LinkFlickr,
        @SerializedName("presskit")
        val presskit: String?,
        @SerializedName("webcast")
        val webcast: String?,
        @SerializedName("youtube_id")
        val youtubeId: String?,
        @SerializedName("article")
        val article: String?,
        @SerializedName("wikipedia")
        val wikipedia: String?,
)
