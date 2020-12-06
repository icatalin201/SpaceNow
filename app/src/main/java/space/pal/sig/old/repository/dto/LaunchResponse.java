package space.pal.sig.old.repository.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LaunchResponse {

    @SerializedName("results")
    private List<PreviewLaunchDto> launches;
    @SerializedName("count")
    private Integer count;

}
