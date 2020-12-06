package space.pal.sig.old.repository.service;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import space.pal.sig.old.repository.dto.ApodDto;

/**
 * SpaceNow
 * Created by Catalin on 7/19/2020
 **/
public interface NasaService {

    @GET("/planetary/apod")
    Call<ApodDto> getPictureOfTheDay(@Query("api_key") String apiKey);

    @GET("/planetary/apod")
    Call<ApodDto> getPictureOfTheDay(@Query("api_key") String apiKey,
                                     @Query("date") String date);

}
