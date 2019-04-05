package space.pal.sig.model;

import android.os.Parcel;
import android.os.Parcelable;

public class VideoPreview implements Parcelable {

    private int id;
    private String name;
    private String news_name;
    private String image;
    private String collection;
    private String mission;

    public VideoPreview() { }

    private VideoPreview(Parcel in) {
        id = in.readInt();
        name = in.readString();
        news_name = in.readString();
        image = in.readString();
        collection = in.readString();
        mission = in.readString();
    }

    public static final Creator<VideoPreview> CREATOR = new Creator<VideoPreview>() {
        @Override
        public VideoPreview createFromParcel(Parcel in) {
            return new VideoPreview(in);
        }

        @Override
        public VideoPreview[] newArray(int size) {
            return new VideoPreview[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNews_name() {
        return news_name;
    }

    public void setNews_name(String news_name) {
        this.news_name = news_name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }

    public String getMission() {
        return mission;
    }

    public void setMission(String mission) {
        this.mission = mission;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(news_name);
        parcel.writeString(image);
        parcel.writeString(collection);
        parcel.writeString(mission);
    }
}
