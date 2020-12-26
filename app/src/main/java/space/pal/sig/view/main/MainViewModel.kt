package space.pal.sig.view.main

import android.app.Application
import androidx.lifecycle.LiveData
import space.pal.sig.model.entity.AstronomyPictureOfTheDay
import space.pal.sig.model.entity.LaunchWithData
import space.pal.sig.model.entity.Roadster
import space.pal.sig.repository.ApodRepository
import space.pal.sig.repository.LaunchRepository
import space.pal.sig.repository.RoadsterRepository
import space.pal.sig.view.BaseViewModel

/**
 * SpaceNow
 * Created by Catalin on 12/20/2020
 **/
class MainViewModel(
        apodRepository: ApodRepository,
        roadsterRepository: RoadsterRepository,
        launchRepository: LaunchRepository,
        application: Application
) : BaseViewModel(application) {

    private val astronomyPictureOfTheDay = apodRepository.findLatest()
    private val nextLaunch = launchRepository.findNext()
    private val latestLaunch = launchRepository.findLatest()
    private val roadster = roadsterRepository.get()

    fun getAstronomyPictureOfTheDay(): LiveData<AstronomyPictureOfTheDay> {
        return astronomyPictureOfTheDay
    }

    fun getRoadster(): LiveData<Roadster> {
        return roadster
    }

    fun getNextLaunch(): LiveData<LaunchWithData> {
        return nextLaunch
    }

    fun getLatestLaunch(): LiveData<LaunchWithData> {
        return latestLaunch
    }

}