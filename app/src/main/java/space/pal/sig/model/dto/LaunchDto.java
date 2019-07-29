package space.pal.sig.model.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LaunchDto {

    @SerializedName("name")
    private String name;
    @SerializedName("windowstart")
    private String windowstart;
    @SerializedName("windowend")
    private String windowend;
    @SerializedName("net")
    private String net;
    @SerializedName("wsstamp")
    private Long wsstamp;
    @SerializedName("westamp")
    private Long westamp;
    @SerializedName("netstamp")
    private Long netstamp;
    @SerializedName("infoURLs")
    private String[] infoURLs;
    @SerializedName("vidURLs")
    private String[] vidURLs;
    @SerializedName("status")
    private Integer status;
    @SerializedName("holdreason")
    private String holdreason;
    @SerializedName("failreason")
    private String failreason;
    @SerializedName("hashtag")
    private String hashtag;
    @SerializedName("location")
    private LocationDto location;
    @SerializedName("rocket")
    private RocketDto rocket;
    @SerializedName("missions")
    private List<MissionDto> missions;
    @SerializedName("lsp")
    private SpaceProviderDto lsp;
}
