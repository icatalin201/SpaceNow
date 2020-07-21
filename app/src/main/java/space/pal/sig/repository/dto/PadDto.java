package space.pal.sig.repository.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PadDto {

    @SerializedName("id")
    private Integer id;
    @SerializedName("name")
    private String name;
    @SerializedName("padType")
    private Integer padType;
    @SerializedName("latitude")
    private String latitude;
    @SerializedName("longitude")
    private String longitude;
    @SerializedName("mapURL")
    private String mapURL;
    @SerializedName("locationid")
    private Integer locationId;
    @SerializedName("wikiURL")
    private String wikiURL;
    @SerializedName("infoURLs")
    private String[] infoURLs;
    @SerializedName("agencies")
    private List<AgencyDto> agencies;

}
