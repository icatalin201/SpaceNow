package space.pal.sig.repository

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
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
        return spaceXApiService
                .getAllLaunches()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
    }

    fun getLaunch(id: String): Single<Launch> {
        return spaceXApiService
                .getLaunch(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
    }

    fun getPastLaunches(): Single<MutableList<Launch>> {
        return spaceXApiService
                .getPastLaunches()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
    }

    fun getUpcomingLaunches(): Single<MutableList<Launch>> {
        return spaceXApiService
                .getUpcomingLaunches()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
    }

    fun getLatestLaunch(): Single<Launch> {
        return spaceXApiService
                .getLatestLaunch()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
    }

    fun getNextLaunch(): Single<Launch> {
        return spaceXApiService
                .getNextLaunch()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
    }

}