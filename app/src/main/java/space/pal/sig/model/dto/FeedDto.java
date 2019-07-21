package space.pal.sig.model.dto;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class FeedDto {

    @SerializedName("title")
    private String title;
    @SerializedName("pub_date")
    private String pubDate;
    @SerializedName("description")
    private String description;
    @SerializedName("guid")
    private String guid;
    @SerializedName("image")
    private String image;
    @SerializedName("image_square")
    private String imageSquare;
    @SerializedName("image_square_large")
    private String imageSquareLarge;
    @SerializedName("thumbnail")
    private String thumbnail;
    @SerializedName("thumbnail_large")
    private String thumbnailLarge;
    @SerializedName("link")
    private String link;

}
