package space.pal.sig.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class NewsRelease implements Parcelable {

    private String name;
    private String news_id;
    private String url;
    private String publication;
    private String mission;
    private String abstract_text;
    private String credits;
    private String thumbnail;
    private String thumbnail_retina;
    private String thumbnail_1x;
    private String thumbnail_2x;
    private String keystone_image_1x;
    private String keystone_image_2x;
    private ArrayList<Integer> release_images;

    public NewsRelease() { }

    public NewsRelease(String name, String news_id, String url, String publication, String mission,
                       String abstract_text, String credits, String thumbnail,
                       String thumbnail_retina, String thumbnail_1x, String thumbnail_2x,
                       String keystone_image_1x, String keystone_image_2x,
                       ArrayList<Integer> release_images) {
        this.name = name;
        this.news_id = news_id;
        this.url = url;
        this.publication = publication;
        this.mission = mission;
        this.abstract_text = abstract_text;
        this.credits = credits;
        this.thumbnail = thumbnail;
        this.thumbnail_retina = thumbnail_retina;
        this.thumbnail_1x = thumbnail_1x;
        this.thumbnail_2x = thumbnail_2x;
        this.keystone_image_1x = keystone_image_1x;
        this.keystone_image_2x = keystone_image_2x;
        this.release_images = release_images;
    }

    private NewsRelease(Parcel in) {
        name = in.readString();
        news_id = in.readString();
        url = in.readString();
        publication = in.readString();
        mission = in.readString();
        abstract_text = in.readString();
        credits = in.readString();
        thumbnail = in.readString();
        thumbnail_retina = in.readString();
        thumbnail_1x = in.readString();
        thumbnail_2x = in.readString();
        keystone_image_1x = in.readString();
        keystone_image_2x = in.readString();
    }

    public static final Creator<NewsRelease> CREATOR = new Creator<NewsRelease>() {
        @Override
        public NewsRelease createFromParcel(Parcel in) {
            return new NewsRelease(in);
        }

        @Override
        public NewsRelease[] newArray(int size) {
            return new NewsRelease[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNews_id() {
        return news_id;
    }

    public void setNews_id(String news_id) {
        this.news_id = news_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPublication() {
        return publication;
    }

    public void setPublication(String publication) {
        this.publication = publication;
    }

    public String getMission() {
        return mission;
    }

    public void setMission(String mission) {
        this.mission = mission;
    }

    public String getAbstract() {
        return abstract_text;
    }

    public void setAbstract(String abstract_text) {
        this.abstract_text = abstract_text;
    }

    public String getCredits() {
        return credits;
    }

    public void setCredits(String credits) {
        this.credits = credits;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getThumbnail_retina() {
        return thumbnail_retina;
    }

    public void setThumbnail_retina(String thumbnail_retina) {
        this.thumbnail_retina = thumbnail_retina;
    }

    public String getThumbnail_1x() {
        return thumbnail_1x;
    }

    public void setThumbnail_1x(String thumbnail_1x) {
        this.thumbnail_1x = thumbnail_1x;
    }

    public String getThumbnail_2x() {
        return thumbnail_2x;
    }

    public void setThumbnail_2x(String thumbnail_2x) {
        this.thumbnail_2x = thumbnail_2x;
    }

    public String getKeystone_image_1x() {
        return keystone_image_1x;
    }

    public void setKeystone_image_1x(String keystone_image_1x) {
        this.keystone_image_1x = keystone_image_1x;
    }

    public String getKeystone_image_2x() {
        return keystone_image_2x;
    }

    public void setKeystone_image_2x(String keystone_image_2x) {
        this.keystone_image_2x = keystone_image_2x;
    }

    public ArrayList<Integer> getRelease_images() {
        return release_images;
    }

    public void setRelease_images(ArrayList<Integer> release_images) {
        this.release_images = release_images;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(news_id);
        parcel.writeString(url);
        parcel.writeString(publication);
        parcel.writeString(mission);
        parcel.writeString(abstract_text);
        parcel.writeString(credits);
        parcel.writeString(thumbnail);
        parcel.writeString(thumbnail_retina);
        parcel.writeString(thumbnail_1x);
        parcel.writeString(thumbnail_2x);
        parcel.writeString(keystone_image_1x);
        parcel.writeString(keystone_image_2x);
    }
}
