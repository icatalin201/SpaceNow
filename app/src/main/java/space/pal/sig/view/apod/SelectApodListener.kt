package space.pal.sig.view.apod

import space.pal.sig.model.dto.AstronomyPictureOfTheDayDto

/**
SpaceNow
Created by Catalin on 12/13/2020
 **/
interface SelectApodListener {
    fun onClick(apod: AstronomyPictureOfTheDayDto)
}