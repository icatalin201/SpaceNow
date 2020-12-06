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

    @GET("capsules/{serial}")
    fun getCapsule(@Path("serial") serial: String): Single<Capsule>

    @GET("capsules/upcoming")
    fun getUpcomingCapsules(): Single<MutableList<Capsule>>

    @GET("capsules/past")
    fun getPastCapsules(): Single<MutableList<Capsule>>

    @GET("cores")
    fun getAllCores(): Single<MutableList<Core>>

    @GET("cores/{serial}")
    fun getCore(@Path("serial") serial: String): Single<Core>

    @GET("cores/upcoming")
    fun getUpcomingCores(): Single<MutableList<Core>>

    @GET("cores/past")
    fun getPastCores(): Single<MutableList<Core>>

    @GET("dragons")
    fun getAllDragons(): Single<MutableList<Dragon>>

    @GET("dragons/{id}")
    fun getDragon(@Path("id") id: String): Single<MutableList<Dragon>>

    @GET("history")
    fun getAllEvents(): Single<MutableList<Event>>

    @GET("history/{id}")
    fun getEvent(@Path("id") id: Long): Single<Event>

    @GET("landpads")
    fun getAllLandingPads(): Single<MutableList<LandingPad>>

    @GET("landpads/{id}")
    fun getPad(@Path("id") id: String): Single<LandingPad>

    @GET("launches")
    fun getAllLaunches(): Single<MutableList<Launch>>

    @GET("launches/{number}")
    fun getLaunch(@Path("number") flightNumber: Long): Single<Launch>

    @GET("launches/past")
    fun getPastLaunches(): Single<MutableList<Launch>>

    @GET("launches/upcoming")
    fun getUpcomingLaunches(): Single<MutableList<Launch>>

    @GET("launches/latest")
    fun getLatestLaunch(): Single<Launch>

    @GET("launches/next")
    fun getNextLaunch(): Single<Launch>

    @GET("launchpads")
    fun getAllLaunchPads(): Single<MutableList<LaunchPad>>

    @GET("launchpads/{id}")
    fun getLaunchPad(@Path("id") id: String): Single<LaunchPad>

    @GET("missions")
    fun getAllMissions(): Single<MutableList<Mission>>

    @GET("missions/{id}")
    fun getMission(@Path("id") id: String): Single<Mission>

    @GET("payloads")
    fun getAllPayloads(): Single<MutableList<Payload>>

    @GET("payloads/{id}")
    fun getPayload(@Path("id") id: String): Single<Payload>

    @GET("rockets")
    fun getAllRockets(): Single<MutableList<Rocket>>

    @GET("rockets/{id}")
    fun getRocket(@Path("id") id: String): Single<Rocket>

    @GET("roadster")
    fun getRoadster(): Single<Roadster>

    @GET("ships")
    fun getAllShips(): Single<MutableList<Ship>>

    @GET("ships/{id}")
    fun getShip(@Path("id") id: String): Single<Ship>

}