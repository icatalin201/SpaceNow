package space.pal.sig.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import space.pal.sig.model.AstronomyPictureOfTheDay
import space.pal.sig.model.Roadster
import space.pal.sig.repository.ApodRepository
import space.pal.sig.repository.RoadsterRepository
import space.pal.sig.view.BaseViewModel

/**
 * SpaceNow
 * Created by Catalin on 12/20/2020
 **/
class MainViewModel(
        apodRepository: ApodRepository,
        roadsterRepository: RoadsterRepository
) : BaseViewModel() {

    private val astronomyPictureOfTheDay = MediatorLiveData<AstronomyPictureOfTheDay>()
    private val roadster = MutableLiveData<Roadster>()

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
    }

    fun getAstronomyPictureOfTheDay(): LiveData<AstronomyPictureOfTheDay> {
        return astronomyPictureOfTheDay
    }

    fun getRoadster(): LiveData<Roadster> {
        return roadster
    }

}