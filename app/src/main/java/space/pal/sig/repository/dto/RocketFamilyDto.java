package space.pal.sig.repository.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import space.pal.sig.repository.dto.AgencyDto;

@Getter
@Setter
public class RocketFamilyDto {

    @SerializedName("id")
    private Integer id;
    @SerializedName("name")
    private String name;
    @SerializedName("agenciess")
    private List<AgencyDto> agencies;

}
