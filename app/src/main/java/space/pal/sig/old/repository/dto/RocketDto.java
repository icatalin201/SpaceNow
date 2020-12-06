package space.pal.sig.old.repository.dto;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RocketDto {

    @SerializedName("id")
    private Integer id;
    @SerializedName("configuration")
    private RocketConfigurationDto configuration;

}
