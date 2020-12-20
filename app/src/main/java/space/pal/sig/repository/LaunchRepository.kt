package space.pal.sig.repository

import io.reactivex.Single
import space.pal.sig.model.Launch
import space.pal.sig.network.SpaceXApiService

/**
 * SpaceNow
 * Created by Catalin on 12/20/2020
 **/
class LaunchRepository(
        private val spaceXApiService: SpaceXApiService
) {

    fun getAllLaunches(): Single<MutableList<Launch>> {
        return spaceXApiService.getAllLaunches()
    }

    fun getLaunch(flightNumber: Long): Single<Launch> {
        return spaceXApiService.getLaunch(flightNumber)
    }

    fun getPastLaunches(): Single<MutableList<Launch>> {
        return spaceXApiService.getPastLaunches()
    }

    fun getUpcomingLaunches(): Single<MutableList<Launch>> {
        return spaceXApiService.getUpcomingLaunches()
    }

    fun getLatestLaunch(): Single<Launch> {
        return spaceXApiService.getLatestLaunch()
    }

    fun getNextLaunch(): Single<Launch> {
        return spaceXApiService.getNextLaunch()
    }

}