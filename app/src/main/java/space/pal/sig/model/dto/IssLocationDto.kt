package space.pal.sig.model.dto

import com.google.gson.annotations.SerializedName

/**
SpaceNow
Created by Catalin on 12/28/2020
 **/
data class IssLocationDto(
        val message: String,
        val timestamp: Long,
        @SerializedName("iss_position")
        val issPosition: IssPosition
)
