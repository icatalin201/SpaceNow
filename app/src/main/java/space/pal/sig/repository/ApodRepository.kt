package space.pal.sig.repository

import androidx.lifecycle.LiveData
import io.reactivex.Single
import space.pal.sig.BuildConfig
import space.pal.sig.database.dao.ApodDao
import space.pal.sig.model.AstronomyPictureOfTheDay
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

    fun findAllImages(): LiveData<MutableList<AstronomyPictureOfTheDay>> {
        return apodDao.findAllImages()
    }

    fun downloadCurrent(): Single<AstronomyPictureOfTheDay> {
        return nasaService.getPictureOfTheDay(BuildConfig.NASA_API_KEY)
    }

    fun downloadByDate(date: String): Single<AstronomyPictureOfTheDay> {
        return nasaService.getPictureOfTheDay(BuildConfig.NASA_API_KEY, date)
    }
}