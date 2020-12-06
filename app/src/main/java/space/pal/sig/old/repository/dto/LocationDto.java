package space.pal.sig.old.repository.dto;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LocationDto {

    @SerializedName("id")
    private Long id;
    @SerializedName("url")
    private String url;
    @SerializedName("name")
    private String name;
    @SerializedName("country_code")
    private String countryCode;
    @SerializedName("map_image")
    private String mapImage;
    @SerializedName("total_launch_count")
    private Integer totalLaunchCount;
    @SerializedName("total_landing_count")
    private Integer totalLandingCount;
}
