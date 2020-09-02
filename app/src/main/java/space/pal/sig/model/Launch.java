package space.pal.sig.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import space.pal.sig.repository.dto.LocationDto;
import space.pal.sig.repository.dto.MissionDto;
import space.pal.sig.repository.dto.RocketDto;
import space.pal.sig.repository.dto.SpaceProviderDto;

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
    private Long id;
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
    private LocationDto location;
    @Nullable
    @ColumnInfo
    private RocketDto rocket;
    @Nullable
    @ColumnInfo
    private List<MissionDto> missions;
    @Nullable
    @ColumnInfo
    private SpaceProviderDto launchSpaceProvider;
    @NonNull
    @ColumnInfo
    private Boolean favorite = false;
}
