package space.pal.sig.repository.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MissionDto {

    @SerializedName("id")
    private Integer id;
    @SerializedName("name")
    private String name;
    @SerializedName("description")
    private String description;
    @SerializedName("wikiURL")
    private String wikiURL;
    @SerializedName("infoURLs")
    private String[] infoURLs;
    @SerializedName("agencies")
    private List<AgencyDto> agencies;
    @SerializedName("payloads")
    private List<PayloadDto> payloads;
}
