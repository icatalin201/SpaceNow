package space.pal.sig.model.dto;

import android.os.Build;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import space.pal.sig.model.Rocket;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RocketDto {

    @SerializedName("id")
    private Integer id;
    @SerializedName("name")
    private String name;
    @SerializedName("wikiURL")
    private String wikiURL;
    @SerializedName("imageURL")
    private String imageURL;
    @SerializedName("configuration")
    private String configuration;
    @SerializedName("defaultPads")
    private String defaultPads;
    @SerializedName("family")
    private RocketFamilyDto family;
//    @SerializedName("infoURLs")
//    private String[] infoURLs;
//    @SerializedName("imageSizes")
//    private Integer[] imageSizes;

    public Rocket toRocket() {
        return Rocket
                .builder()
                .rocketId(id)
                .name(name)
                .wikiURL(wikiURL)
                .imageURL(imageURL)
                .build();
    }

    public static List<Rocket> toRockets(List<RocketDto> rocketDtos) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return rocketDtos.stream().map(RocketDto::toRocket).collect(Collectors.toList());
        } else {
            List<Rocket> rockets = new ArrayList<>();
            for (RocketDto rocketDto : rocketDtos) {
                rockets.add(rocketDto.toRocket());
            }
            return rockets;
        }
    }

    public static RocketDto from(Rocket rocket) {
        return RocketDto
                .builder()
                .id(rocket.getRocketId())
                .name(rocket.getName())
                .wikiURL(rocket.getWikiURL())
                .imageURL(rocket.getImageURL())
                .build();
    }

}
