package space.pal.sig.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * SpaceNow
 * Created by Catalin on 12/26/2020
 **/
@Entity(tableName = "crew_members")
data class CrewMember(
        @ColumnInfo
        @PrimaryKey(autoGenerate = false)
        val id: String,
        @ColumnInfo
        val name: String?,
        @ColumnInfo
        val status: String,
        @ColumnInfo
        val agency: String?,
        @ColumnInfo
        val image: String?,
        @ColumnInfo
        val wikipedia: String?,
        @ColumnInfo
        val launches: List<String>
)
