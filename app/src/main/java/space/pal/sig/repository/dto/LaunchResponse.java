package space.pal.sig.repository.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import space.pal.sig.repository.dto.LaunchDto;

@Getter
@Setter
public class LaunchResponse {

    @SerializedName("launches")
    private List<LaunchDto> launches;
    @SerializedName("total")
    private Integer total;
    @SerializedName("offset")
    private Integer offset;
    @SerializedName("count")
    private Integer count;

}
