package space.pal.sig.model.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpaceFlightNewsDto {

    @SerializedName("title")
    private String title;
    @SerializedName("news_site")
    private String newsSite;
    @SerializedName("news_site_long")
    private String newsSiteLong;
    @SerializedName("url")
    private String url;
    @SerializedName("featured_image")
    private String featuredImage;
    @SerializedName("_id")
    private String snapiId;
    @SerializedName("date_published")
    private Long datePublished;
    @SerializedName("date_added")
    private Long dateAdded;
    @SerializedName("categories")
    private List<String> categories;
    @SerializedName("tags")
    private List<String> tags;
    @SerializedName("launches")
    private List<String> launches;
    @SerializedName("events")
    private List<String> events;
    @SerializedName("ll")
    private List<String> launchLibraryIds;

}
