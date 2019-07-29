package space.pal.sig.api;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import space.pal.sig.model.dto.LaunchResponse;
import space.pal.sig.model.dto.RocketsResponse;

public interface LaunchService {

    @GET("/1.4/rocket")
    Single<RocketsResponse> getRockets(@Query("offset") int offset,
                                       @Query("limit") int count);

    @GET("1.4/launch/")
    Single<LaunchResponse> getLaunches(@Query("offset") int offset,
                                       @Query("limit") int count,
                                       @Query("sort") String sort,
                                       @Query("startdate") String startdate,
                                       @Query("enddate") String enddate);

    @GET("/1.4/launch/next/{count}")
    Single<LaunchResponse> getNextLaunches(@Path("count") int count);

    @GET("/1.4/rocket/{id}")
    Single<RocketsResponse> getRocket(@Path("id") int id);

}
