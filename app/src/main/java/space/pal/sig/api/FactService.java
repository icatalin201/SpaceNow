package space.pal.sig.api;

import io.reactivex.Single;
import retrofit2.http.GET;
import space.pal.sig.model.dto.FactDto;
import space.pal.sig.model.dto.SpaceResponse;

public interface FactService {

    @GET("/facts.php")
    Single<SpaceResponse<FactDto>> download();

}
