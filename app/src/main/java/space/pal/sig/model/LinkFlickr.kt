package space.pal.sig.model

import com.google.gson.annotations.SerializedName

/**
 * SpaceNow
 * Created by Catalin on 12/22/2020
 **/
data class LinkFlickr(
        @SerializedName("small")
        val small: List<String>,
        @SerializedName("original")
        val original: List<String>
)
