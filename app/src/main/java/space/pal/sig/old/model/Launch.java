package space.pal.sig.old.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import space.pal.sig.old.repository.dto.MissionDto;
import space.pal.sig.old.repository.dto.PadDto;
import space.pal.sig.old.repository.dto.RocketDto;
import space.pal.sig.old.repository.dto.SpaceProviderDto;

/**
 * SpaceNow
 * Created by Catalin on 7/19/2020
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity(tableName = "launches")
public class Launch {

    @NonNull
    @PrimaryKey
    @ColumnInfo
    private String id;
    @NonNull
    @ColumnInfo
    private String name;
    @NonNull
    @ColumnInfo
    private String date;
    @NonNull
    @ColumnInfo
    private Long timestamp;
    @NonNull
    @ColumnInfo
    private LaunchStatus status;
    @Nullable
    @ColumnInfo
    private String holdReason;
    @Nullable
    @ColumnInfo
    private String failReason;
    @Nullable
    @ColumnInfo
    private String infoUrls;
    @Nullable
    @ColumnInfo
    private String videoUrls;
    @Nullable
    @ColumnInfo
    private RocketDto rocket;
    @Nullable
    @ColumnInfo
    private PadDto pad;
    @Nullable
    @ColumnInfo
    private MissionDto mission;
    @Nullable
    @ColumnInfo
    private SpaceProviderDto launchSpaceProvider;
    @NonNull
    @ColumnInfo
    private Boolean favorite = false;
}
