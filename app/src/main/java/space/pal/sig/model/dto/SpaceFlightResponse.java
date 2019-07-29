package space.pal.sig.model.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpaceFlightResponse {

    @SerializedName("docs")
    private List<SpaceFlightNewsDto> docs;
    @SerializedName("totalDocs")
    private Integer totalDocs;
    @SerializedName("limit")
    private Integer limit;
    @SerializedName("hasPrevPage")
    private Boolean hasPrevPage;
    @SerializedName("hasNextPage")
    private Boolean hasNextPage;
    @SerializedName("page")
    private Integer page;
    @SerializedName("totalPages")
    private Integer totalPages;
    @SerializedName("pagingCounter")
    private Integer pagingCounter;
    @SerializedName("prevPage")
    private Integer prevPage;
    @SerializedName("nextPage")
    private Integer nextPage;

}
