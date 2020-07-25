package space.pal.sig.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * SpaceNow
 * Created by Catalin on 7/24/2020
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(tableName = "apod")
public class Apod {

    @NonNull
    @PrimaryKey
    @ColumnInfo
    private String id;
    @NonNull
    @ColumnInfo
    private Date date;
    @NonNull
    @ColumnInfo
    private String explanation;
    @NonNull
    @ColumnInfo
    private String url;
    @NonNull
    @ColumnInfo
    private String title;
    @ColumnInfo
    private String copyright;
    @NonNull
    @ColumnInfo
    private MediaType type;
    @NonNull
    @ColumnInfo
    private Integer year;
    @NonNull
    @ColumnInfo
    private Integer month;
    @NonNull
    @ColumnInfo
    private Integer day;

}
