package space.pal.sig.old.repository.service;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import space.pal.sig.old.repository.dto.LaunchDto;
import space.pal.sig.old.repository.dto.LaunchResponse;

public interface LaunchService {

    @GET("launch/{id}")
    Call<LaunchDto> getLaunch(@Path("id") String id);

    @GET("launch/upcoming")
    Call<LaunchResponse> getUpcomingLaunches(@Query("limit") int count);

}
