package space.pal.sig.network

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import space.pal.sig.model.*

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
    fun getAllCrewMembers(): Single<MutableList<CrewMember>>

    @GET("crew/{id}")
    fun getCrewMembers(@Path("id") id: String): Single<CrewMember>

    @GET("dragons")
    fun getAllDragons(): Single<MutableList<Dragon>>

    @GET("dragons/{id}")
    fun getDragon(@Path("id") id: String): Single<MutableList<Dragon>>

    @GET("landpads")
    fun getAllLandpads(): Single<MutableList<LandingPad>>

    @GET("landpads/{id}")
    fun getLandpad(@Path("id") id: String): Single<LandingPad>

    @GET("launches/past")
    fun getPastLaunches(): Single<MutableList<Launch>>

    @GET("launches/upcoming")
    fun getUpcomingLaunches(): Single<MutableList<Launch>>

    @GET("launches/latest")
    fun getLatestLaunch(): Single<Launch>

    @GET("launches/next")
    fun getNextLaunch(): Single<Launch>

    @GET("launches")
    fun getAllLaunches(): Single<MutableList<Launch>>

    @GET("launches/{id}")
    fun getLaunch(@Path("id") id: String): Single<Launch>

    @GET("launchpads")
    fun getAllLaunchpads(): Single<MutableList<LaunchPad>>

    @GET("launchpads/{id}")
    fun getLaunchpad(@Path("id") id: String): Single<LaunchPad>

    @GET("payloads")
    fun getAllPayloads(): Single<MutableList<Payload>>

    @GET("payloads/{id}")
    fun getPayload(@Path("id") id: String): Single<Payload>

    @GET("roadster")
    fun getRoadster(): Single<Roadster>

    @GET("rockets")
    fun getAllRockets(): Single<MutableList<Rocket>>

    @GET("rockets/{id}")
    fun getRocket(@Path("id") id: String): Single<Rocket>

    @GET("ships")
    fun getAllShips(): Single<MutableList<Ship>>

    @GET("ships/{id}")
    fun getShip(@Path("id") id: String): Single<Ship>

    @GET("history")
    fun getAllEvents(): Single<MutableList<Event>>

    @GET("history/{id}")
    fun getEvent(@Path("id") id: Long): Single<Event>
}