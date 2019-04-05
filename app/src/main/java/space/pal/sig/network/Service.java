package space.pal.sig.network;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import space.pal.sig.model.Apod;
import space.pal.sig.model.Feed;
import space.pal.sig.model.Glossary;
import space.pal.sig.model.NewsPreview;
import space.pal.sig.model.VideoDetails;
import space.pal.sig.model.VideoPreview;
import space.pal.sig.util.Utils;

public interface Service {

    @GET(Utils.NEWS)
    Call<List<NewsPreview>> getNewsPreview(@Query("page") int page);

    @GET(Utils.GLOSSARY_ALL)
    Call<List<Glossary>> getGlossaryAll(@Query("page") String page);

    @GET(Utils.FEED + Utils.ESA_FEED)
    Call<List<Feed>> getExternalESAFeed(@Query("page") int page);

    @GET(Utils.FEED + Utils.JWST_FEED)
    Call<List<Feed>> getExternalJWSTFeed(@Query("page") int page);

    @GET(Utils.FEED + Utils.ST_LIVE_FEED)
    Call<List<Feed>> getExternalSTFeed(@Query("page") int page);

    @GET(Utils.VIDEOS)
    Call<List<VideoPreview>> getVideosPreview(@Query("page") int page);

    @GET(Utils.VIDEO)
    Call<VideoDetails> getVideoInfo(@Path("video_id") int video_id);

    @GET(Utils.APOD)
    Call<Apod> getAstronomyPictureOfTheDayWithDate(@Query("date") String date,
                                                   @Query("hd") boolean hd,
                                                   @Query("api_key") String apiKey);

    @GET(Utils.APOD)
    Call<Apod> getAstronomyPictureOfTheDay(@Query("api_key") String apikey);
}
