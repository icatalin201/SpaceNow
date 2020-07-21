package space.pal.sig.repository.dto;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RocketDto {

    @SerializedName("id")
    private Integer id;
    @SerializedName("name")
    private String name;
    @SerializedName("wikiURL")
    private String wikiURL;
    @SerializedName("imageURL")
    private String imageURL;
    @SerializedName("configuration")
    private String configuration;
    @SerializedName("defaultPads")
    private String defaultPads;
    @SerializedName("family")
    private RocketFamilyDto family;
    @SerializedName("infoURLs")
    private String[] infoURLs;

}
