package space.pal.sig.model;

public class VideoFile {

    private String file_url;
    private int file_size;
    private int width;
    private int height;
    private String frame_rate;
    private String format;

    public VideoFile() { }

    public String getFile_url() {
        return file_url;
    }

    public void setFile_url(String file_url) {
        this.file_url = file_url;
    }

    public int getFile_size() {
        return file_size;
    }

    public void setFile_size(int file_size) {
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

    public String getFrame_rate() {
        return frame_rate;
    }

    public void setFrame_rate(String frame_rate) {
        this.frame_rate = frame_rate;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}
