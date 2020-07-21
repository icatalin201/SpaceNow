package space.pal.sig.repository.dto;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import space.pal.sig.model.Launch;
import space.pal.sig.model.LaunchStatus;

@Getter
@Setter
public class LaunchDto {

    @SerializedName("id")
    private Long id;
    @SerializedName("name")
    private String name;
    @SerializedName("net")
    private String net;
    @SerializedName("netstamp")
    private Long netStamp;
    @SerializedName("infoURLs")
    private String[] infoURLs;
    @SerializedName("vidURLs")
    private String[] vidURLs;
    @SerializedName("status")
    private Integer status;
    @SerializedName("holdreason")
    private String holdReason;
    @SerializedName("failreason")
    private String failReason;
    @SerializedName("location")
    private LocationDto location;
    @SerializedName("rocket")
    private RocketDto rocket;
    @SerializedName("missions")
    private List<MissionDto> missions;
    @SerializedName("lsp")
    private SpaceProviderDto lsp;

    public Launch toLaunchModel() {
        Launch launch = new Launch();
        launch.setId(id);
        launch.setDate(net);
        launch.setTimestamp(netStamp * 1000);
        launch.setName(name);
        launch.setFailReason(failReason);
        launch.setHoldReason(holdReason);
        launch.setInfoUrls(TextUtils.join(",", infoURLs));
        launch.setVideoUrls(TextUtils.join(",", vidURLs));
        launch.setLocation(location);
        launch.setLaunchSpaceProvider(lsp);
        launch.setMissions(missions);
        launch.setRocket(rocket);
        launch.setStatus(LaunchStatus.fromValue(status));
        return launch;
    }
}
