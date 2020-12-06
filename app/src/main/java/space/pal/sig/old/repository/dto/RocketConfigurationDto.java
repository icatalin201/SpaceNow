package space.pal.sig.old.repository.dto;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

/**
 * SpaceNow
 * Created by Catalin on 12/6/2020
 **/
@Getter
@Setter
public class RocketConfigurationDto {
    @SerializedName("id")
    private Long id;
    @SerializedName("url")
    private String url;
    @SerializedName("name")
    private String name;
    @SerializedName("description")
    private String description;
    @SerializedName("family")
    private String family;
    @SerializedName("fullName")
    private String fullName;
    @SerializedName("variant")
    private String variant;
    @SerializedName("alias")
    private String alias;
    @SerializedName("length")
    private Double length;
    @SerializedName("diameter")
    private Double diameter;
    @SerializedName("launch_mass")
    private Double launchMass;
    @SerializedName("total_launch_count")
    private Integer totalLaunchCount;
    @SerializedName("consecutive_successful_launches")
    private Integer consecutiveSuccessfulLaunches;
    @SerializedName("successful_launches")
    private Integer successfulLaunches;
    @SerializedName("failed_launches")
    private Integer failedLaunches;
    @SerializedName("pending_launches")
    private Integer pendingLaunches;
    @SerializedName("info_url")
    private String infoUrl;
    @SerializedName("image_url")
    private String imageUrl;
}
