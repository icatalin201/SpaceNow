package space.pal.sig.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * SpaceNow
 * Created by Catalin on 12/24/2020
 **/
@Entity(tableName = "facts")
data class Fact(
        @ColumnInfo
        @PrimaryKey(autoGenerate = false)
        val content: String
)
