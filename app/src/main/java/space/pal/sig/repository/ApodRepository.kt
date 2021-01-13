package space.pal.sig.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import io.reactivex.Single
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
        val apodMediator = MediatorLiveData<AstronomyPictureOfTheDay>()
        apodMediator.addSource(apodDao.findByDate(date)) { apod ->
            if (apod == null) {
                downloadByDate(date)
                        .subscribe(
                                { save(it.toAstronomyPictureOfTheDay()) },
                                { it.printStackTrace(); apodMediator.postValue(null) })
            } else {
                apodMediator.postValue(apod)
            }
        }
        return apodMediator
    }

    fun findLatest(): LiveData<AstronomyPictureOfTheDay> {
        return apodDao.findLatest()
    }

    fun downloadCurrent(): Single<AstronomyPictureOfTheDayDto> {
        return nasaService
                .getPictureOfTheDay(BuildConfig.NASA_API_KEY)
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
    }

    private fun downloadByDate(date: String): Single<AstronomyPictureOfTheDayDto> {
        return nasaService
                .getPictureOfTheDay(BuildConfig.NASA_API_KEY, date)
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
    }
}