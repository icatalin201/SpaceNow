package space.pal.sig.old.model.dto;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;
import space.pal.sig.old.model.Glossary;

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
