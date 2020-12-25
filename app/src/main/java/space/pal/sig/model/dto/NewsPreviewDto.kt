package space.pal.sig.model.dto

import com.google.gson.annotations.SerializedName

/**
 * SpaceNow
 * Created by Catalin on 12/25/2020
 **/
data class NewsPreviewDto(
        @SerializedName("news_id")
        val id: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("url")
        val url: String
)