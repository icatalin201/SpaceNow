package space.pal.sig.repository

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import space.pal.sig.model.dto.IssLocationDto
import space.pal.sig.model.dto.IssPassDto
import space.pal.sig.network.IssService

/**
SpaceNow
Created by Catalin on 12/28/2020
 **/
class IssRepository(
        private val issService: IssService
) {

    fun getLiveLocation(): Single<IssLocationDto> {
        return issService.getLiveLocation()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun getPassTimes(latitude: Double, longitude: Double): Single<IssPassDto> {
        return issService.getPassTimes(latitude, longitude)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

}