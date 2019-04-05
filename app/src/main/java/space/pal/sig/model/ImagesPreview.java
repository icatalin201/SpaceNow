package space.pal.sig.model;

public class ImagesPreview {

    private int id;
    private String name;
    private String news_name;
    private String collection;
    private String mission;

    public ImagesPreview() { }

    public ImagesPreview(int id, String name, String news_name, String collection, String mission) {
        this.id = id;
        this.name = name;
        this.news_name = news_name;
        this.collection = collection;
        this.mission = mission;
    }

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
}
