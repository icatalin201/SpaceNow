package space.pal.sig.model.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpaceResponse<T> {

    private List<T> result;

}
