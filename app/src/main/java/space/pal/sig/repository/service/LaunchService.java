package space.pal.sig.repository.service;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import space.pal.sig.repository.dto.LaunchResponse;

public interface LaunchService {

    @GET("1.4/launch/")
    Call<LaunchResponse> getLaunches(@Query("offset") int offset,
                                     @Query("limit") int count,
                                     @Query("startdate") String startDate,
                                     @Query("enddate") String endDate);

    @GET("/1.4/launch/{id}")
    Call<LaunchResponse> getLaunch(@Path("id") int id);

    @GET("/1.4/launch/next/{count}")
    Call<LaunchResponse> getNextLaunches(@Path("count") int count);

}
