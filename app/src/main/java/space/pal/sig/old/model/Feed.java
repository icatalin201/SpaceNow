package space.pal.sig.old.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(tableName = "feed")
public class Feed {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo
    private Long id;
    @ColumnInfo
    private String title;
    @ColumnInfo
    private String pubDate;
    @ColumnInfo
    private String description;
    @ColumnInfo
    private String guid;
    @ColumnInfo
    private String image;
    @ColumnInfo
    private String imageSquare;
    @ColumnInfo
    private String imageSquareLarge;
    @ColumnInfo
    private String thumbnail;
    @ColumnInfo
    private String thumbnailLarge;
    @ColumnInfo
    private String link;

}
