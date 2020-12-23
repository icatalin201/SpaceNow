package space.pal.sig.model.entity

import androidx.room.Embedded
import androidx.room.Relation

/**
 * SpaceNow
 * Created by Catalin on 12/23/2020
 **/
data class LaunchWithData(
        @Embedded
        val launch: Launch,
        @Relation(parentColumn = "rocketId", entityColumn = "id", entity = Rocket::class)
        val rocket: Rocket,
        @Relation(parentColumn = "launchpadId", entityColumn = "id", entity = LaunchPad::class)
        val launchpad: LaunchPad
)