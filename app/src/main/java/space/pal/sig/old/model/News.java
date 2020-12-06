package space.pal.sig.old.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * SpaceNow
 * Created by Catalin on 7/18/2020
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity(tableName = "news")
public class News {

    @NonNull
    @PrimaryKey
    @ColumnInfo
    private String id;
    @NonNull
    @ColumnInfo
    private String title;
    @NonNull
    @ColumnInfo
    private String description;
    @NonNull
    @ColumnInfo
    private Date date;
    @NonNull
    @ColumnInfo
    private String thumbnail;
    @NonNull
    @ColumnInfo
    private String image;
    @NonNull
    @ColumnInfo
    private String url;
    @NonNull
    @ColumnInfo
    private NewsSource source;

}
