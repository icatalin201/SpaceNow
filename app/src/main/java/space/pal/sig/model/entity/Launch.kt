package space.pal.sig.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import space.pal.sig.model.dto.DataLinks
import space.pal.sig.model.dto.Failure
import space.pal.sig.model.dto.LaunchCore
import space.pal.sig.model.dto.LaunchFairing

/**
 * SpaceNow
 * Created by Catalin on 12/23/2020
 **/
@Entity(tableName = "launches")
data class Launch(
        @PrimaryKey(autoGenerate = false)
        @ColumnInfo
        val id: String,
        @ColumnInfo
        val flightNumber: Long,
        @ColumnInfo
        val name: String,
        @ColumnInfo
        val dateUtc: String,
        @ColumnInfo
        val dateUnix: Long,
        @ColumnInfo
        val dateLocal: String,
        @ColumnInfo
        val staticFireDateUtc: String?,
        @ColumnInfo
        val staticFireDateUnix: Long?,
        @ColumnInfo
        val tbd: Boolean,
        @ColumnInfo
        val net: Boolean,
        @ColumnInfo
        val window: Long?,
        @ColumnInfo
        val rocketId: String,
        @ColumnInfo
        val success: Boolean?,
        @ColumnInfo
        val failures: List<Failure>,
        @ColumnInfo
        val upcoming: Boolean,
        @ColumnInfo
        val details: String?,
        @ColumnInfo
        val crewIds: List<String>,
        @ColumnInfo
        val shipsIds: List<String>,
        @ColumnInfo
        val capsulesIds: List<String>,
        @ColumnInfo
        val payloadsIds: List<String>,
        @ColumnInfo
        val launchpadId: String?,
        @ColumnInfo
        val links: DataLinks,
        @ColumnInfo
        val autoUpdate: Boolean,
        @ColumnInfo
        val cores: List<LaunchCore>,
        @ColumnInfo
        val fairings: LaunchFairing?,
)
