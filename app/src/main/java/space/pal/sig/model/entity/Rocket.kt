package space.pal.sig.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import space.pal.sig.model.dto.*

/**
 * SpaceNow
 * Created by Catalin on 12/23/2020
 **/
@Entity(tableName = "rockets")
data class Rocket(
        @ColumnInfo
        @PrimaryKey(autoGenerate = false)
        val id: String,
        @ColumnInfo
        val name: String,
        @ColumnInfo
        val type: String,
        @ColumnInfo
        val active: Boolean,
        @ColumnInfo
        val stages: Int,
        @ColumnInfo
        val boosters: Int,
        @ColumnInfo
        val costPerLaunch: Double,
        @ColumnInfo
        val successRatePct: Int,
        @ColumnInfo
        val firstFlight: String,
        @ColumnInfo
        val country: String,
        @ColumnInfo
        val company: String,
        @ColumnInfo
        val height: Diameter,
        @ColumnInfo
        val diameter: Diameter,
        @ColumnInfo
        val mass: PayloadMass,
        @ColumnInfo
        val payloadWeights: List<PayloadWeight>,
        @ColumnInfo
        val firstStage: RocketFirstStage,
        @ColumnInfo
        val secondStage: RocketSecondStage,
        @ColumnInfo
        val engines: RocketEngines,
        @ColumnInfo
        val landingLegs: RocketLandingLegs,
        @ColumnInfo
        val wikipedia: String,
        @ColumnInfo
        val description: String,
        @ColumnInfo
        val images: List<String>
)
