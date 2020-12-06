package space.pal.sig.old.repository.dto;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MissionDto {

    @SerializedName("id")
    private Long id;
    @SerializedName("name")
    private String name;
    @SerializedName("description")
    private String description;
}
