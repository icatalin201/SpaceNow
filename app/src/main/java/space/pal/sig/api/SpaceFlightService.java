package space.pal.sig.api;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;
import space.pal.sig.model.dto.SpaceFlightResponse;

public interface SpaceFlightService {

    @GET("/api/v1/articles")
    Single<SpaceFlightResponse> getArticles(@Query("page") int page, @Query("limit") int limit);
}
