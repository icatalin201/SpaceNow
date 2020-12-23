package space.pal.sig.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * SpaceNow
 * Created by Catalin on 12/23/2020
 **/
@Entity(tableName = "roadster")
data class Roadster(
        @ColumnInfo
        @PrimaryKey(autoGenerate = false)
        val name: String,
        @ColumnInfo
        val launchDateUnix: Long,
        @ColumnInfo
        val launchDateUtc: String,
        @ColumnInfo
        val launchMassKg: Double,
        @ColumnInfo
        val launchMassLbs: Double,
        @ColumnInfo
        val noradId: Double,
        @ColumnInfo
        val epochJd: Double,
        @ColumnInfo
        val orbitType: String,
        @ColumnInfo
        val apoapsisAu: Double,
        @ColumnInfo
        val periapsisAu: Double,
        @ColumnInfo
        val semiMajorAxisAu: Double,
        @ColumnInfo
        val eccentricity: Double,
        @ColumnInfo
        val inclination: Double,
        @ColumnInfo
        val longitude: Double,
        @ColumnInfo
        val periapsisArg: Double,
        @ColumnInfo
        val periodDays: Double,
        @ColumnInfo
        val speedKph: Double,
        @ColumnInfo
        val speedMph: Double,
        @ColumnInfo
        val earthDistanceKm: Double,
        @ColumnInfo
        val earthDistanceMi: Double,
        @ColumnInfo
        val marsDistanceKm: Double,
        @ColumnInfo
        val marsDistanceMi: Double,
        @ColumnInfo
        val wikipedia: String,
        @ColumnInfo
        val details: String,
        @ColumnInfo
        val video: String,
        @ColumnInfo
        val images: List<String>
)
