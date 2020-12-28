package space.pal.sig.network

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import space.pal.sig.model.dto.IssLocationDto
import space.pal.sig.model.dto.IssPassDto

/**
SpaceNow
Created by Catalin on 12/28/2020
 **/
interface IssService {

    @GET("iss-now.json")
    fun getLiveLocation(): Single<IssLocationDto>

    @GET("iss-pass.json")
    fun getPassTimes(
            @Query("latitude") latitude: Double,
            @Query("longitude") longitude: Double): Single<IssPassDto>

}