package space.pal.sig.repository

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import space.pal.sig.model.Roadster
import space.pal.sig.network.SpaceXApiService

/**
 * SpaceNow
 * Created by Catalin on 12/20/2020
 **/
class RoadsterRepository(
        private val spaceXApiService: SpaceXApiService
) {

    fun getRoadster(): Single<Roadster> {
        return spaceXApiService
                .getRoadster()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
    }

}