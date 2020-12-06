package space.pal.sig.old.repository.dto;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AgencyDto {

    @SerializedName("id")
    private Integer id;
    @SerializedName("name")
    private String name;
    @SerializedName("abbrev")
    private String abbrev;
    @SerializedName("countryCode")
    private String countryCode;
    @SerializedName("wikiURL")
    private String wikiURL;
    @SerializedName("infoURLs")
    private String[] infoURLs;
}
