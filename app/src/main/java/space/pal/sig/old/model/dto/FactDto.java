package space.pal.sig.old.model.dto;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;
import space.pal.sig.old.model.Fact;

@Setter
@Getter
public class FactDto {

    @SerializedName("name")
    private String name;

    public Fact toFact() {
        return Fact.builder().name(name).build();
    }
}
