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
@Entity(tableName = "glossary", indices = @Index(value = {"name"}, unique = true))
public class Glossary {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo
    private Long id;
    @ColumnInfo
    private String name;
    @ColumnInfo
    private String definition;

}
