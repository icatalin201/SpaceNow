package space.pal.sig.model.dto

import com.google.gson.annotations.SerializedName
import space.pal.sig.model.entity.News
import space.pal.sig.model.entity.NewsSource
import java.util.*

/**
 * SpaceNow
 * Created by Catalin on 12/25/2020
 **/
data class NewsDto(
        @SerializedName("name", alternate = ["title"])
        val title: String,
        @SerializedName("description", alternate = ["abstract"])
        val description: String,
        @SerializedName("pub_date", alternate = ["publication"])
        val date: Date,
        @SerializedName("link", alternate = ["url"])
        val link: String,
        @SerializedName("image", alternate = ["keystone_image_2x"])
        val image: String,
        @SerializedName("thumbnail")
        val thumbnail: String
) {

    fun toNews(source: NewsSource): News {
        val url = link.replace("http://", "https://")
        return News(
                title, description, date, url, image, thumbnail, source
        )
    }

}
