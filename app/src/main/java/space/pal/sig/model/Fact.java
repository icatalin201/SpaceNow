package space.pal.sig.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(tableName = "facts")
public class Fact {

    @NonNull
    @PrimaryKey
    @ColumnInfo
    private String id;
    @NonNull
    @ColumnInfo
    private String name;

}
