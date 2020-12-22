package space.pal.sig.model

import com.google.gson.annotations.SerializedName

/**
SpaceNow
Created by Catalin on 12/6/2020
 **/
data class Event(
        @SerializedName("id")
        val id: String,
        @SerializedName("title")
        val title: String?,
        @SerializedName("event_date_utc")
        val dateUtc: String?,
        @SerializedName("event_date_unix")
        val dateUnix: Long?,
        @SerializedName("details")
        val details: String?,
        @SerializedName("links")
        val links: DataLinks
)
