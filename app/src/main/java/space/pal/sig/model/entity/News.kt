package space.pal.sig.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

/**
 * SpaceNow
 * Created by Catalin on 12/24/2020
 **/
@Entity(tableName = "news")
data class News(
        @PrimaryKey(autoGenerate = false)
        @ColumnInfo
        val id: String,
        @ColumnInfo
        val title: String,
        @ColumnInfo
        val description: String,
        @ColumnInfo
        val image: String,
        val date: Date,
        @ColumnInfo
        val thumbnail: String,
        @ColumnInfo
        val url: String,
        @ColumnInfo
        val source: NewsSource
)
