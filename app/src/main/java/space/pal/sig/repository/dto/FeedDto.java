package space.pal.sig.repository.dto;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import space.pal.sig.model.News;
import space.pal.sig.model.NewsSource;

import static space.pal.sig.util.DateUtil.HUBBLE_DATE_FORMAT;
import static space.pal.sig.util.DateUtil.parseDate;

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

    public News toNewsModel(NewsSource source) {
        News news = new News();
        news.setId(title + guid);
        news.setDescription(description);
        news.setTitle(title);
        news.setUrl(link);
        news.setDate(parseDate(pubDate, HUBBLE_DATE_FORMAT));
        news.setImage(imageSquareLarge == null ? imageSquare : imageSquareLarge);
        news.setSource(source);
        news.setThumbnail(thumbnailLarge);
        return news;
    }

}
