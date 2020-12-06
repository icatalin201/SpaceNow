package space.pal.sig.model

import com.google.gson.annotations.SerializedName

/**
SpaceNow
Created by Catalin on 12/6/2020
 **/
data class LaunchSite(
        @SerializedName("site_id")
        val id: String,
        @SerializedName("site_name")
        val name: String,
        @SerializedName("site_name_long")
        val nameLong: String
)
