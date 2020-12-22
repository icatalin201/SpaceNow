package space.pal.sig.model

import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * SpaceNow
 * Created by Catalin on 12/22/2020
 **/
data class CrewMember(
        @SerializedName("id")
        val id: String,
        @SerializedName("name")
        val name: String?,
        @SerializedName("status")
        val status: String,
        @SerializedName("agency")
        val agency: String?,
        @SerializedName("image")
        val image: String?,
        @SerializedName("wikipedia")
        val wikipedia: String?,
        @SerializedName("launches")
        val launches: List<String>
)
