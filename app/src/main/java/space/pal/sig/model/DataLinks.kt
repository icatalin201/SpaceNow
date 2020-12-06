package space.pal.sig.model

import com.google.gson.annotations.SerializedName

/**
SpaceNow
Created by Catalin on 12/6/2020
 **/
data class DataLinks(
        @SerializedName("article", alternate = ["article_link"])
        val article: String?,
        @SerializedName("wikipedia")
        val wikipedia: String?,
        @SerializedName("video_link")
        val videoLink: String?,
        @SerializedName("youtube_id")
        val youtubeId: String?,
        @SerializedName("flickr_images")
        val images: MutableList<String>?
)
