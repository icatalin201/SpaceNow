package space.pal.sig.old.repository.service;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import space.pal.sig.old.repository.dto.FeedDto;
import space.pal.sig.old.repository.dto.NewsDto;
import space.pal.sig.old.repository.dto.NewsPreviewDto;

/**
 * SpaceNow
 * Created by Catalin on 7/19/2020
 **/
public interface HubbleService {

    @GET("/api/v3/news")
    Call<List<NewsPreviewDto>> getHubbleFeed(@Query("page") int page);

    @GET("/api/v3/news?page=all")
    Call<List<NewsPreviewDto>> getHubbleFeed();

    @GET("/api/v3/news_release/{id}")
    Call<NewsDto> getHubbleNews(@Path("id") String newsId);

    @GET("/api/v3/external_feed/esa_feed")
    Call<List<FeedDto>> getEuropeanSpaceAgencyFeed(@Query("page") int page);

    @GET("/api/v3/external_feed/esa_feed?page=all")
    Call<List<FeedDto>> getEuropeanSpaceAgencyFeed();

    @GET("/api/v3/external_feed/jwst_feed")
    Call<List<FeedDto>> getJamesWebbSpaceTelescopeFeed(@Query("page") int page);

    @GET("/api/v3/external_feed/jwst_feed?page=all")
    Call<List<FeedDto>> getJamesWebbSpaceTelescopeFeed();

    @GET("/api/v3/external_feed/st_live")
    Call<List<FeedDto>> getSpaceTelescopeLiveFeed(@Query("page") int page);

    @GET("/api/v3/external_feed/st_live?page=all")
    Call<List<FeedDto>> getSpaceTelescopeLiveFeed();

}
