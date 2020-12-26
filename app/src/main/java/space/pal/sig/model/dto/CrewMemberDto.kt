package space.pal.sig.model.dto

import com.google.gson.annotations.SerializedName
import space.pal.sig.model.entity.CrewMember

/**
 * SpaceNow
 * Created by Catalin on 12/22/2020
 **/
data class CrewMemberDto(
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
) {
    fun toCrewMember(): CrewMember {
        return CrewMember(id, name, status, agency, image, wikipedia, launches)
    }
}
