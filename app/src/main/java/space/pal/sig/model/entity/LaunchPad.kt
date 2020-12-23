package space.pal.sig.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * SpaceNow
 * Created by Catalin on 12/23/2020
 **/
@Entity(tableName = "launchpads")
data class LaunchPad(
        @ColumnInfo
        @PrimaryKey(autoGenerate = false)
        val id: String,
        @ColumnInfo
        val name: String?,
        @ColumnInfo
        val fullName: String?,
        @ColumnInfo
        val status: String,
        @ColumnInfo
        val type: String?,
        @ColumnInfo
        val locality: String?,
        @ColumnInfo
        val region: String?,
        @ColumnInfo
        val timezone: String?,
        @ColumnInfo
        val latitude: Double?,
        @ColumnInfo
        val longitude: Double?,
        @ColumnInfo
        val landingAttempts: Int,
        @ColumnInfo
        val landingSuccesses: Int,
        @ColumnInfo
        val wikipedia: String?,
        @ColumnInfo
        val details: String?,
        @ColumnInfo
        val launches: List<String>,
        @ColumnInfo
        val rockets: List<String>
)
