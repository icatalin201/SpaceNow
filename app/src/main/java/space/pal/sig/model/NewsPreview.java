package space.pal.sig.model;

import android.os.Parcel;
import android.os.Parcelable;

public class NewsPreview implements Parcelable {

    private String name;
    private String news_id;
    private String url;

    public NewsPreview() { }

    public NewsPreview(String name, String news_id, String url) {
        this.name = name;
        this.news_id = news_id;
        this.url = url;
    }

    private NewsPreview(Parcel in) {
        name = in.readString();
        news_id = in.readString();
        url = in.readString();
    }

    public static final Creator<NewsPreview> CREATOR = new Creator<NewsPreview>() {
        @Override
        public NewsPreview createFromParcel(Parcel in) {
            return new NewsPreview(in);
        }

        @Override
        public NewsPreview[] newArray(int size) {
            return new NewsPreview[size];
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(news_id);
        parcel.writeString(url);
    }
}
