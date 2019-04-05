package space.pal.sig.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Feed implements Parcelable {

    private String title;
    private String pub_date;
    private String description;
    private String guid;
    private String image;
    private String image_square;
    private String image_square_large;
    private String thumbnail;
    private String thumbnail_large;
    private String link;

    public Feed() { }

    public Feed(String title, String pub_date, String description, String guid, String image,
                String image_square, String image_square_large, String thumbnail,
                String thumbnail_large, String link) {
        this.title = title;
        this.pub_date = pub_date;
        this.description = description;
        this.guid = guid;
        this.image = image;
        this.image_square = image_square;
        this.image_square_large = image_square_large;
        this.thumbnail = thumbnail;
        this.thumbnail_large = thumbnail_large;
        this.link = link;
    }

    private Feed(Parcel in) {
        title = in.readString();
        pub_date = in.readString();
        description = in.readString();
        guid = in.readString();
        image = in.readString();
        image_square = in.readString();
        image_square_large = in.readString();
        thumbnail = in.readString();
        thumbnail_large = in.readString();
        link = in.readString();
    }

    public static final Creator<Feed> CREATOR = new Creator<Feed>() {
        @Override
        public Feed createFromParcel(Parcel in) {
            return new Feed(in);
        }

        @Override
        public Feed[] newArray(int size) {
            return new Feed[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPub_date() {
        return pub_date;
    }

    public void setPub_date(String pub_date) {
        this.pub_date = pub_date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage_square() {
        return image_square;
    }

    public void setImage_square(String image_square) {
        this.image_square = image_square;
    }

    public String getImage_square_large() {
        return image_square_large;
    }

    public void setImage_square_large(String image_square_large) {
        this.image_square_large = image_square_large;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getThumbnail_large() {
        return thumbnail_large;
    }

    public void setThumbnail_large(String thumbnail_large) {
        this.thumbnail_large = thumbnail_large;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(pub_date);
        parcel.writeString(description);
        parcel.writeString(guid);
        parcel.writeString(image);
        parcel.writeString(image_square);
        parcel.writeString(image_square_large);
        parcel.writeString(thumbnail);
        parcel.writeString(thumbnail_large);
        parcel.writeString(link);
    }
}
