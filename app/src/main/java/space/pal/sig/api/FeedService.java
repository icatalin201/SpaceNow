package space.pal.sig.api;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import space.pal.sig.model.dto.FeedDto;
import space.pal.sig.model.dto.NewsDto;
import space.pal.sig.model.dto.NewsPreviewDto;

public interface FeedService {

    @GET("/api/v3/news")
    Single<List<NewsPreviewDto>> hubbleFeed(@Query("page") int page);

    @GET("/api/v3/external_feed/esa_feed")
    Single<List<FeedDto>> esaFeed(@Query("page") int page);

    @GET("/api/v3/external_feed/jwst_feed")
    Single<List<FeedDto>> jwstFeed(@Query("page") int page);

    @GET("/api/v3/external_feed/st_live")
    Single<List<FeedDto>> spaceTelescopeFeed(@Query("page") int page);

    @GET("/api/v3/external_feed/esa_feed?page=all")
    Single<List<FeedDto>> esaFeed();

    @GET("/api/v3/external_feed/jwst_feed?page=all")
    Single<List<FeedDto>> jwstFeed();

    @GET("/api/v3/external_feed/st_live?page=all")
    Single<List<FeedDto>> spaceTelescopeFeed();

    @GET("/api/v3/news?page=all")
    Single<List<NewsPreviewDto>> hubbleFeed();

    @GET("/api/v3/news_release/{id}")
    Single<NewsDto> hubbleNews(@Path("id") String newsId);

    @GET("/api/v3/news_release/{id}")
    Call<NewsDto> hubbleNewsSync(@Path("id") String newsId);

}
