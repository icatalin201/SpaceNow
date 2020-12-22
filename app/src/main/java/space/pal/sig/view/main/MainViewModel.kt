package space.pal.sig.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import space.pal.sig.model.AstronomyPictureOfTheDay
import space.pal.sig.model.Launch
import space.pal.sig.model.Roadster
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
        launchRepository: LaunchRepository
) : BaseViewModel() {

    private val astronomyPictureOfTheDay = MediatorLiveData<AstronomyPictureOfTheDay>()
    private val roadster = MutableLiveData<Roadster>()
    private val nextLaunch = MutableLiveData<Launch>()

    init {
        val apodDisposable = apodRepository.downloadCurrent()
                .subscribe(
                        { astronomyPictureOfTheDay.value = it },
                        { it.printStackTrace() }
                )
        val roadsterDisposable = roadsterRepository.getRoadster()
                .subscribe(
                        { roadster.value = it },
                        { it.printStackTrace() }
                )
        val launchDisposable = launchRepository.getNextLaunch()
                .subscribe(
                        { nextLaunch.value = it },
                        { it.printStackTrace() }
                )
        compositeDisposable.add(apodDisposable)
        compositeDisposable.add(roadsterDisposable)
        compositeDisposable.add(launchDisposable)
    }

    fun getAstronomyPictureOfTheDay(): LiveData<AstronomyPictureOfTheDay> {
        return astronomyPictureOfTheDay
    }

    fun getRoadster(): LiveData<Roadster> {
        return roadster
    }

    fun getNextLaunch(): LiveData<Launch> {
        return nextLaunch
    }

}