package space.pal.sig.old.repository.dto;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PadDto {

    @SerializedName("id")
    private Integer id;
    @SerializedName("name")
    private String name;
    @SerializedName("latitude")
    private String latitude;
    @SerializedName("longitude")
    private String longitude;
    @SerializedName("map_url")
    private String mapUrl;
    @SerializedName("wiki_url")
    private String wikiURL;
    @SerializedName("info_url")
    private String infoURL;
    @SerializedName("map_image")
    private String mapImage;
    @SerializedName("total_launch_count")
    private Integer totalLaunchCount;
    @SerializedName("agency_id")
    private Long agencyId;
    @SerializedName("location")
    private LocationDto location;
}
