package space.pal.sig.model.dto;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PayloadDto {

    @SerializedName("id")
    private Integer id;
    @SerializedName("name")
    private String name;
    @SerializedName("description")
    private String description;
    @SerializedName("dimensions")
    private String dimensions;
    @SerializedName("weight")
    private String weight;
    @SerializedName("total")
    private Integer total;

}
