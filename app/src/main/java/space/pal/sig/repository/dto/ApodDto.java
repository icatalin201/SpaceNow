package space.pal.sig.repository.dto;

import com.google.gson.annotations.SerializedName;

import java.util.Calendar;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import space.pal.sig.model.Apod;
import space.pal.sig.model.MediaType;

import static space.pal.sig.util.DateUtil.parseDate;

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
        Date d = parseDate(date, "yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        Apod apod = new Apod();
        apod.setCopyright(copyright);
        apod.setExplanation(explanation);
        apod.setTitle(title);
        apod.setUrl(hdUrl != null ? hdUrl : url);
        apod.setType(MediaType.valueOf(mediaType.toUpperCase()));
        apod.setDate(d);
        apod.setId(date + title);
        apod.setYear(calendar.get(Calendar.YEAR));
        apod.setMonth(calendar.get(Calendar.MONTH));
        apod.setDay(calendar.get(Calendar.DAY_OF_MONTH));
        return apod;
    }

}
