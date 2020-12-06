package space.pal.sig.old.repository.dto;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;
import space.pal.sig.old.model.Launch;
import space.pal.sig.old.model.LaunchStatus;

@Getter
@Setter
public class LaunchDto {

    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("slug")
    private String slug;
    @SerializedName("status")
    private space.pal.sig.old.repository.dto.LaunchStatus status;
    @SerializedName("net")
    private String net;
    @SerializedName("window_end")
    private String windowEnd;
    @SerializedName("window_start")
    private String windowStart;
    @SerializedName("inhold")
    private Boolean inHold;
    @SerializedName("tbdtime")
    private Boolean tbdTime;
    @SerializedName("tbddate")
    private Boolean tbdDate;
    @SerializedName("probability")
    private Integer probability;
    @SerializedName("holdreason")
    private String holdReason;
    @SerializedName("failreason")
    private String failReason;
    @SerializedName("hashtag")
    private String hashTag;
    @SerializedName("launch_service_provider")
    private SpaceProviderDto lsp;
    @SerializedName("infoURLs")
    private String[] infoURLs;
    @SerializedName("vidURLs")
    private String[] vidURLs;
    @SerializedName("rocket")
    private RocketDto rocket;
    @SerializedName("pad")
    private PadDto pad;
    @SerializedName("mission")
    private MissionDto mission;


    public Launch toLaunchModel() {
        Launch launch = new Launch();
        launch.setId(id);
        launch.setDate(net);
        launch.setTimestamp(1000L);
        launch.setName(name);
        launch.setFailReason(failReason);
        launch.setHoldReason(holdReason);
        launch.setInfoUrls(TextUtils.join(",", infoURLs));
        launch.setVideoUrls(TextUtils.join(",", vidURLs));
        launch.setLaunchSpaceProvider(lsp);
        launch.setMission(mission);
        launch.setRocket(rocket);
        launch.setPad(pad);
        launch.setStatus(LaunchStatus.fromValue(status.getId()));
        return launch;
    }
}
