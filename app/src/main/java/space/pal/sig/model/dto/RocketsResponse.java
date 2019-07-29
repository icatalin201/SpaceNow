package space.pal.sig.model.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RocketsResponse {

    private List<RocketDto> rockets;
    private Integer total;
    private Integer offset;
    private Integer count;

}
