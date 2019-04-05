package space.pal.sig.model;

import java.util.List;

public class VideoDetails {

    private String name;
    private String short_description;
    private String youtube_id;
    private String teachertube_id;
    private String credits;
    private String news_name;
    private String mission;
    private String collection;
    private String image;
    private String image_retina;
    private HTML5Video html_5_video;
    private List<VideoFile> video_files;

    public VideoDetails() { }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShort_description() {
        return short_description;
    }

    public void setShort_description(String short_description) {
        this.short_description = short_description;
    }

    public String getYoutube_id() {
        return youtube_id;
    }

    public void setYoutube_id(String youtube_id) {
        this.youtube_id = youtube_id;
    }

    public String getTeachertube_id() {
        return teachertube_id;
    }

    public void setTeachertube_id(String teachertube_id) {
        this.teachertube_id = teachertube_id;
    }

    public String getCredits() {
        return credits;
    }

    public void setCredits(String credits) {
        this.credits = credits;
    }

    public String getNews_name() {
        return news_name;
    }

    public void setNews_name(String news_name) {
        this.news_name = news_name;
    }

    public String getMission() {
        return mission;
    }

    public void setMission(String mission) {
        this.mission = mission;
    }

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage_retina() {
        return image_retina;
    }

    public void setImage_retina(String image_retina) {
        this.image_retina = image_retina;
    }

    public HTML5Video getHtml_5_video() {
        return html_5_video;
    }

    public void setHtml_5_video(HTML5Video html_5_video) {
        this.html_5_video = html_5_video;
    }

    public List<VideoFile> getVideo_files() {
        return video_files;
    }

    public void setVideo_files(List<VideoFile> video_files) {
        this.video_files = video_files;
    }
}
