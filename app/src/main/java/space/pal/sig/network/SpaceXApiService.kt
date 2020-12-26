package space.pal.sig.network

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import space.pal.sig.model.dto.*

/**
SpaceNow
Created by Catalin on 12/6/2020
 **/
interface SpaceXApiService {

    @GET("capsules")
    fun getAllCapsules(): Single<MutableList<Capsule>>

    @GET("capsules/{id}")
    fun getCapsule(@Path("id") id: String): Single<Capsule>

    @GET("cores")
    fun getAllCores(): Single<MutableList<Core>>

    @GET("cores/{id}")
    fun getCore(@Path("id") id: String): Single<Core>

    @GET("crew")
    fun getAllCrewMembers(): Single<MutableList<CrewMemberDto>>

    @GET("crew/{id}")
    fun getCrewMembers(@Path("id") id: String): Single<CrewMemberDto>

    @GET("dragons")
    fun getAllDragons(): Single<MutableList<Dragon>>

    @GET("dragons/{id}")
    fun getDragon(@Path("id") id: String): Single<MutableList<Dragon>>

    @GET("landpads")
    fun getAllLandpads(): Single<MutableList<LandingPad>>

    @GET("landpads/{id}")
    fun getLandpad(@Path("id") id: String): Single<LandingPad>

    @GET("launches/past")
    fun getPastLaunches(): Single<MutableList<LaunchDto>>

    @GET("launches/upcoming")
    fun getUpcomingLaunches(): Single<MutableList<LaunchDto>>

    @GET("launches/latest")
    fun getLatestLaunch(): Single<LaunchDto>

    @GET("launches/next")
    fun getNextLaunch(): Single<LaunchDto>

    @GET("launches")
    fun getAllLaunches(): Single<MutableList<LaunchDto>>

    @GET("launches/{id}")
    fun getLaunch(@Path("id") id: String): Single<LaunchDto>

    @GET("launchpads")
    fun getAllLaunchpads(): Single<MutableList<LaunchPadDto>>

    @GET("launchpads/{id}")
    fun getLaunchpad(@Path("id") id: String): Single<LaunchPadDto>

    @GET("payloads")
    fun getAllPayloads(): Single<MutableList<Payload>>

    @GET("payloads/{id}")
    fun getPayload(@Path("id") id: String): Single<Payload>

    @GET("roadster")
    fun getRoadster(): Single<RoadsterDto>

    @GET("rockets")
    fun getAllRockets(): Single<MutableList<RocketDto>>

    @GET("rockets/{id}")
    fun getRocket(@Path("id") id: String): Single<RocketDto>

    @GET("ships")
    fun getAllShips(): Single<MutableList<Ship>>

    @GET("ships/{id}")
    fun getShip(@Path("id") id: String): Single<Ship>

    @GET("history")
    fun getAllEvents(): Single<MutableList<Event>>

    @GET("history/{id}")
    fun getEvent(@Path("id") id: Long): Single<Event>
}