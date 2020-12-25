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
        val title: String,
        @ColumnInfo
        val description: String?,
        @ColumnInfo
        val date: Date,
        @ColumnInfo
        val link: String,
        @ColumnInfo
        val image: String?,
        @ColumnInfo
        val thumbnail: String?,
        @ColumnInfo
        val source: NewsSource,
)
