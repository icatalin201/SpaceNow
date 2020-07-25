package space.pal.sig.repository.service;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import space.pal.sig.repository.dto.ApodDto;

/**
 * SpaceNow
 * Created by Catalin on 7/19/2020
 **/
public interface NasaService {

    String API_KEY = "BST2Npgb7FKgBIVzIW2Dd3qARcQbwgk1jdY5Hc28";

    @GET("/planetary/apod")
    Call<ApodDto> getPictureOfTheDay(@Query("api_key") String apiKey);

    @GET("/planetary/apod")
    Call<ApodDto> getPictureOfTheDay(@Query("api_key") String apiKey,
                                     @Query("date") String date);

}
