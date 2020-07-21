package space.pal.sig.old.model;

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
@Entity(tableName = "rocket", indices = @Index(value = {"rocket_id"}, unique = true))
public class Rocket {

    @PrimaryKey(autoGenerate = true)
    private Long id;
    @ColumnInfo(name = "rocket_id")
    private Integer rocketId;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "wikiURL")
    private String wikiURL;
    @ColumnInfo(name = "imageURL")
    private String imageURL;

}
