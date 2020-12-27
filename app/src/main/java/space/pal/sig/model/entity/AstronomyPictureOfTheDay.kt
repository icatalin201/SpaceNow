package space.pal.sig.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

/**
 * SpaceNow
 * Created by Catalin on 12/23/2020
 **/
@Entity(tableName = "astronomy_pictures_of_the_day")
data class AstronomyPictureOfTheDay(
        @PrimaryKey(autoGenerate = false)
        @ColumnInfo
        val date: Date,
        @ColumnInfo
        val explanation: String,
        @ColumnInfo
        val url: String,
        @ColumnInfo
        val title: String,
        @ColumnInfo
        val copyright: String?,
        @ColumnInfo
        val serviceVersion: String,
        @ColumnInfo
        val mediaType: String,
        @ColumnInfo
        val hdUrl: String
)
