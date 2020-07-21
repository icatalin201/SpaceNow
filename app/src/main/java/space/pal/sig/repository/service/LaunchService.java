package space.pal.sig.repository.service;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import space.pal.sig.repository.dto.LaunchResponse;
import space.pal.sig.old.model.dto.RocketsResponse;

public interface LaunchService {

    @GET("/1.4/rocket")
    Call<RocketsResponse> getRockets(@Query("offset") int offset,
                                     @Query("limit") int count);

    @GET("1.4/launch/")
    Call<LaunchResponse> getLaunches(@Query("offset") int offset,
                                     @Query("limit") int count,
                                     @Query("startdate") String startDate,
                                     @Query("enddate") String endDate);

    @GET("/1.4/launch/next/{count}")
    Call<LaunchResponse> getNextLaunches(@Path("count") int count);

    @GET("/1.4/rocket/{id}")
    Call<RocketsResponse> getRocket(@Path("id") int id);

}
