package space.pal.sig.model;

import java.util.ArrayList;

public class ImagesDetails {

    private String name;
    private String description;
    private String credits;
    private String news_name;
    private String mission;
    private String collection;
    private ArrayList<ImageFile> image_File_files;

    public ImagesDetails() { }

    public ImagesDetails(String name, String description, String credits, String news_name,
                         String mission, String collection, ArrayList<ImageFile> image_File_files) {
        this.name = name;
        this.description = description;
        this.credits = credits;
        this.news_name = news_name;
        this.mission = mission;
        this.collection = collection;
        this.image_File_files = image_File_files;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public ArrayList<ImageFile> getImage_File_files() {
        return image_File_files;
    }

    public void setImage_File_files(ArrayList<ImageFile> image_File_files) {
        this.image_File_files = image_File_files;
    }
}
