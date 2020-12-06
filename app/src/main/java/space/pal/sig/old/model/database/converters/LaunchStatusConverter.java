package space.pal.sig.old.model.database.converters;

import androidx.room.TypeConverter;

import space.pal.sig.old.model.LaunchStatus;

/**
 * SpaceNow
 * Created by Catalin on 7/19/2020
 **/
public class LaunchStatusConverter {

    @TypeConverter
    public static LaunchStatus fromString(String status) {
        return status == null ? null : LaunchStatus.valueOf(status);
    }

    @TypeConverter
    public static String toString(LaunchStatus status) {
        return status == null ? null : status.name();
    }

}
