package space.pal.sig.model.database.converters;

import androidx.room.TypeConverter;

import space.pal.sig.model.MediaType;

/**
 * SpaceNow
 * Created by Catalin on 7/24/2020
 **/
public class MediaTypeConverter {

    @TypeConverter
    public static MediaType fromString(String type) {
        return type == null ? null : MediaType.valueOf(type);
    }

    @TypeConverter
    public static String toString(MediaType type) {
        return type == null ? null : type.name();
    }

}
