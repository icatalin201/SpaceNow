package space.pal.sig.model.dto;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;
import space.pal.sig.model.Apod;

@Getter
@Setter
public class ApodDto {

    @SerializedName(value = "date", alternate = "date_created")
    private String date;
    @SerializedName(value = "explanation", alternate = "description")
    private String explanation;
    @SerializedName(value = "url", alternate = "image")
    private String url;
    @SerializedName("title")
    private String title;
    @SerializedName(value = "copyright", alternate = "photographer")
    private String copyright;
    @SerializedName("service_version")
    private String serviceVersion;
    @SerializedName("media_type")
    private String mediaType;
    @SerializedName(value = "hdurl", alternate = "hd_image")
    private String hdUrl;

    public Apod toApod() {
        return Apod.builder()
                .date(date)
                .copyright(copyright)
                .title(title)
                .url(url)
                .hdUrl(hdUrl)
                .explanation(explanation)
                .build();
    }

}
