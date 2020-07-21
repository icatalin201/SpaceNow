package space.pal.sig.model.database.converters;

import androidx.room.TypeConverter;

import space.pal.sig.model.NewsSource;

/**
 * SpaceNow
 * Created by Catalin on 7/19/2020
 **/
public class NewsSourceConverter {

    @TypeConverter
    public static NewsSource fromString(String source) {
        return source == null ? null : NewsSource.valueOf(source);
    }

    @TypeConverter
    public static String toString(NewsSource source) {
        return source == null ? null : source.name();
    }

}
