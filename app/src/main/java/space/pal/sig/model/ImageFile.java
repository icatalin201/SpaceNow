package space.pal.sig.model;

public class ImageFile {

    private String file_url;
    private long file_size;
    private int width;
    private int height;

    public ImageFile() { }

    public ImageFile(String file_url, long file_size, int width, int height) {
        this.file_url = file_url;
        this.file_size = file_size;
        this.width = width;
        this.height = height;
    }

    public String getFile_url() {
        return file_url;
    }

    public void setFile_url(String file_url) {
        this.file_url = file_url;
    }

    public long getFile_size() {
        return file_size;
    }

    public void setFile_size(long file_size) {
        this.file_size = file_size;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
