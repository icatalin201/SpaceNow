package space.pal.sig.api;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;
import space.pal.sig.model.dto.ApodDto;

public interface ApodService {

    @GET("/planetary/apod")
    Single<ApodDto> apod(@Query("api_key") String apiKey);

    @GET("/planetary/apod")
    Single<ApodDto> apodWithDate(@Query("api_key") String apiKey,
                                 @Query("date") String date);
}
