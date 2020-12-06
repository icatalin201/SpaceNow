package space.pal.sig.network

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import space.pal.sig.model.AstronomyPictureOfTheDay

/**
SpaceNow
Created by Catalin on 12/6/2020
 **/
interface NasaApiService {

    @GET("planetary/apod")
    fun getPictureOfTheDay(@Query("api_key") apiKey: String): Single<AstronomyPictureOfTheDay>

    @GET("planetary/apod")
    fun getPictureOfTheDay(@Query("api_key") apiKey: String,
                           @Query("date") date: String): Single<AstronomyPictureOfTheDay>

}