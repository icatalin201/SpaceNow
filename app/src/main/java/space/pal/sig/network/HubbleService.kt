package space.pal.sig.network

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import space.pal.sig.model.dto.NewsDto
import space.pal.sig.model.dto.NewsPreviewDto

/**
 * SpaceNow
 * Created by Catalin on 12/25/2020
 **/
interface HubbleService {

    @GET("news")
    fun getHubbleFeed(@Query("page") page: Int): Single<List<NewsPreviewDto>>

    @GET("news?page=all")
    fun getHubbleFeed(): Single<List<NewsPreviewDto>>

    @GET("news_release/{id}")
    fun getHubbleNews(@Path("id") newsId: String): Single<NewsDto>

    @GET("external_feed/esa_feed")
    fun getEuropeanSpaceAgencyFeed(@Query("page") page: Int): Single<List<NewsDto>>

    @GET("external_feed/esa_feed?page=all")
    fun getEuropeanSpaceAgencyFeed(): Single<List<NewsDto>>

    @GET("external_feed/jwst_feed")
    fun getJamesWebbSpaceTelescopeFeed(@Query("page") page: Int): Single<List<NewsDto>>

    @GET("external_feed/jwst_feed?page=all")
    fun getJamesWebbSpaceTelescopeFeed(): Single<List<NewsDto>>

    @GET("external_feed/st_live")
    fun getSpaceTelescopeLiveFeed(@Query("page") page: Int): Single<List<NewsDto>>

    @GET("external_feed/st_live?page=all")
    fun getSpaceTelescopeLiveFeed(): Single<List<NewsDto>>

}