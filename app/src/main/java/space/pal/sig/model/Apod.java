package space.pal.sig.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
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
@Entity(tableName = "apod", indices = @Index(value = {"date"}, unique = true))
public class Apod {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo
    private Long id;
    @ColumnInfo
    private String date;
    @ColumnInfo
    private String explanation;
    @ColumnInfo
    private String url;
    @ColumnInfo
    private String hdUrl;
    @ColumnInfo
    private String title;
    @ColumnInfo
    private String copyright;

}
