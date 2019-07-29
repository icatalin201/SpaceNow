package space.pal.sig.model.dto;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpaceProviderDto {

    @SerializedName("name")
    private String name;
    @SerializedName("abbrev")
    private String abbrev;
    @SerializedName("countryCode")
    private String countryCode;
    @SerializedName("wikiURL")
    private String wikiURL;
    @SerializedName("type")
    private int type;

}
