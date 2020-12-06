package space.pal.sig.old.repository.dto;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpaceProviderDto {

    @SerializedName("id")
    private Long id;
    @SerializedName("url")
    private String url;
    @SerializedName("name")
    private String name;
    @SerializedName("abbrev")
    private String abbrev;
    @SerializedName("country_code")
    private String countryCode;
    @SerializedName("wiki_url")
    private String wikiURL;
    @SerializedName("info_url")
    private String infoURL;
    @SerializedName("type")
    private String type;
    @SerializedName("logo_url")
    private String logoUrl;
    @SerializedName("image_url")
    private String imageUrl;
    @SerializedName("nation_url")
    private String nationUrl;
}
