package space.pal.sig.model.dto;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;
import space.pal.sig.model.Glossary;

@Getter
@Setter
public class GlossaryDto {

    @SerializedName("name")
    private String name;
    @SerializedName("definition")
    private String definition;

    public Glossary toGlossary() {
        return Glossary
                .builder()
                .name(name)
                .definition(definition)
                .build();
    }

}
