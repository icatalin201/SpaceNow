package space.pal.sig.repository

import androidx.lifecycle.LiveData
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import space.pal.sig.BuildConfig
import space.pal.sig.database.dao.ApodDao
import space.pal.sig.model.dto.AstronomyPictureOfTheDayDto
import space.pal.sig.model.entity.AstronomyPictureOfTheDay
import space.pal.sig.network.NasaApiService

/**
 * SpaceNow
 * Created by Catalin on 7/24/2020
 */
class ApodRepository(
        private val apodDao: ApodDao,
        private val nasaService: NasaApiService
) {

    fun save(astronomyPictureOfTheDay: AstronomyPictureOfTheDay) {
        apodDao.save(astronomyPictureOfTheDay)
    }

    fun findByDate(date: String): LiveData<AstronomyPictureOfTheDay> {
        return apodDao.findByDate(date)
    }

    fun findLatest(): LiveData<AstronomyPictureOfTheDay> {
        return apodDao.findLatest()
    }

    fun downloadCurrent(): Single<AstronomyPictureOfTheDayDto> {
        return nasaService
                .getPictureOfTheDay(BuildConfig.NASA_API_KEY)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
    }

    fun downloadByDate(date: String): Single<AstronomyPictureOfTheDayDto> {
        return nasaService
                .getPictureOfTheDay(BuildConfig.NASA_API_KEY, date)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
    }
}