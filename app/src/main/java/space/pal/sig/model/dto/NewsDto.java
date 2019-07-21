package space.pal.sig.model.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewsDto {

    @SerializedName("name")
    private String name;
    @SerializedName("news_id")
    private String newsId;
    @SerializedName("url")
    private String url;
    @SerializedName("publication")
    private String publication;
    @SerializedName("mission")
    private String mission;
    @SerializedName("abstract")
    private String abstractText;
    @SerializedName("credits")
    private String credits;
    @SerializedName("thumbnail")
    private String thumbnail;
    @SerializedName("thumbnail_retina")
    private String thumbnailRetina;
    @SerializedName("thumbnail_1x")
    private String thumbnail1x;
    @SerializedName("thumbnail_2x")
    private String thumbnail2x;
    @SerializedName("keystone_image_1x")
    private String keystoneImage1x;
    @SerializedName("keystone_image_2x")
    private String keystoneImage2x;
    @SerializedName("release_images")
    private List<Integer> releaseImages;

}
