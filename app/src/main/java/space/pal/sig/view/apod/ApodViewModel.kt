package space.pal.sig.view.apod

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import space.pal.sig.model.AstronomyPictureOfTheDay
import space.pal.sig.repository.ApodRepository
import space.pal.sig.view.BaseViewModel

/**
SpaceNow
Created by Catalin on 12/13/2020
 **/
class ApodViewModel(
        private val apodRepository: ApodRepository
) : BaseViewModel() {

    private val astronomyPictureOfTheDay = MediatorLiveData<AstronomyPictureOfTheDay>()

    init {
        astronomyPictureOfTheDay.addSource(
                apodRepository.findAllImages()
        ) { list ->
            astronomyPictureOfTheDay.value = list[0]
        }
    }

    fun getAstronomyPictureOfTheDay(): LiveData<AstronomyPictureOfTheDay> {
        return astronomyPictureOfTheDay
    }

}