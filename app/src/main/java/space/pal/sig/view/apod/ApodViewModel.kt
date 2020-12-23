package space.pal.sig.view.apod

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import space.pal.sig.model.dto.AstronomyPictureOfTheDayDto
import space.pal.sig.repository.ApodRepository
import space.pal.sig.view.BaseViewModel

/**
SpaceNow
Created by Catalin on 12/13/2020
 **/
class ApodViewModel(
        private val apodRepository: ApodRepository,
        application: Application
) : BaseViewModel(application) {

    private val astronomyPictureOfTheDay = MediatorLiveData<AstronomyPictureOfTheDayDto>()

    fun getAstronomyPictureOfTheDay(): LiveData<AstronomyPictureOfTheDayDto> {
        return astronomyPictureOfTheDay
    }

}