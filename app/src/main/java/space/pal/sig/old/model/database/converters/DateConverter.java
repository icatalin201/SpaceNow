package space.pal.sig.old.model.database.converters;

import androidx.room.TypeConverter;

import java.util.Date;

/**
 * SpaceNow
 * Created by Catalin on 7/19/2020
 **/
public class DateConverter {

    @TypeConverter
    public static Date fromTimestamp(Long time) {
        return time == null ? null : new Date(time);
    }

    @TypeConverter
    public static Long toTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

}
