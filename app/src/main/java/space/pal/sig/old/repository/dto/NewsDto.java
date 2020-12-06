package space.pal.sig.old.repository.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import space.pal.sig.old.model.News;
import space.pal.sig.old.model.NewsSource;

import static space.pal.sig.old.util.DateUtil.HUBBLE_DATE_FORMAT;
import static space.pal.sig.old.util.DateUtil.parseDate;

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

    public News toNewsModel() {
        News news = new News();
        news.setId(newsId);
        news.setDescription(abstractText);
        news.setTitle(name);
        news.setUrl(url);
        news.setDate(parseDate(publication, HUBBLE_DATE_FORMAT));
        news.setImage(keystoneImage2x);
        news.setSource(NewsSource.HUBBLE);
        news.setThumbnail(thumbnail2x);
        return news;
    }

}
