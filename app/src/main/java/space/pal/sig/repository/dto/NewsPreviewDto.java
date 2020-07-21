package space.pal.sig.repository.dto;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NewsPreviewDto {

    @SerializedName("name")
    private String name;
    @SerializedName("news_id")
    private String newsId;
    @SerializedName("url")
    private String url;

}
