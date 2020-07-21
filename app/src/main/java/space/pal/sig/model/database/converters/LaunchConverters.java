package space.pal.sig.model.database.converters;

import androidx.room.TypeConverter;

import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;

import space.pal.sig.repository.dto.LocationDto;
import space.pal.sig.repository.dto.MissionDto;
import space.pal.sig.repository.dto.RocketDto;
import space.pal.sig.repository.dto.SpaceProviderDto;

/**
 * SpaceNow
 * Created by Catalin on 7/19/2020
 **/
public class LaunchConverters {

    private static final Gson gson = new Gson();

    @TypeConverter
    public static LocationDto locationFromString(String location) {
        return location == null ? null : gson.fromJson(location, LocationDto.class);
    }

    @TypeConverter
    public static String locationToString(LocationDto location) {
        return location == null ? null : gson.toJson(location);
    }

    @TypeConverter
    public static RocketDto rocketFromString(String rocket) {
        return rocket == null ? null : gson.fromJson(rocket, RocketDto.class);
    }

    @TypeConverter
    public static String rocketToString(RocketDto rocket) {
        return rocket == null ? null : gson.toJson(rocket);
    }

    @TypeConverter
    public static List<MissionDto> missionsFromString(String missions) {
        return missions == null ? null : Arrays.asList(gson.fromJson(missions, MissionDto[].class));
    }

    @TypeConverter
    public static String missionsToString(List<MissionDto> missions) {
        return missions == null ? null : gson.toJson(missions);
    }

    @TypeConverter
    public static SpaceProviderDto spaceProviderFromString(String spaceProvider) {
        return spaceProvider == null ? null : gson.fromJson(spaceProvider, SpaceProviderDto.class);
    }

    @TypeConverter
    public static String spaceProviderToString(SpaceProviderDto spaceProvider) {
        return spaceProvider == null ? null : gson.toJson(spaceProvider);
    }

}
