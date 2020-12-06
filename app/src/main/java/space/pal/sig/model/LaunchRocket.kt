package space.pal.sig.model

import com.google.gson.annotations.SerializedName

/**
SpaceNow
Created by Catalin on 12/6/2020
 **/
data class LaunchRocket(
        @SerializedName("rocket_id")
        val rocketId: String,
        @SerializedName("rocket_name")
        val name: String,
        @SerializedName("rocket_type")
        val type: String,
        @SerializedName("first_stage")
        val firstStage: LaunchRocketFirstStage,
        @SerializedName("second_stage")
        val secondStage: LaunchRocketSecondStage,
        @SerializedName("fairings")
        val fairings: LaunchRocketFairings
)
