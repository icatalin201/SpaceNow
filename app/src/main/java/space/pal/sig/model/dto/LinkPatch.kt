package space.pal.sig.model.dto

import com.google.gson.annotations.SerializedName

/**
 * SpaceNow
 * Created by Catalin on 12/22/2020
 **/
data class LinkPatch(
        @SerializedName("small")
        val small: String?,
        @SerializedName("large")
        val large: String?
)
