package space.pal.sig.api;

import io.reactivex.Single;
import retrofit2.http.GET;
import space.pal.sig.model.dto.ApodDto;
import space.pal.sig.model.dto.SpaceResponse;

public interface GalleryService {

    @GET("/images.php")
    Single<SpaceResponse<ApodDto>> download();
}
