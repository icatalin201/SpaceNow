package space.pal.sig.view.apod

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import androidx.work.WorkRequest
import space.pal.sig.model.entity.AstronomyPictureOfTheDay
import space.pal.sig.repository.ApodRepository
import space.pal.sig.service.download.DownloadImageWorker
import space.pal.sig.util.displayDate
import space.pal.sig.util.formatDate
import space.pal.sig.view.BaseViewModel
import java.util.*

/**
SpaceNow
Created by Catalin on 12/13/2020
 **/
class ApodViewModel(
        private val apodRepository: ApodRepository,
        application: Application
) : BaseViewModel(application) {

    companion object {
        const val IMAGE_URL = "image.url"
    }

    private val astronomyPictureOfTheDay = MediatorLiveData<AstronomyPictureOfTheDay>()
    private val workManager = WorkManager.getInstance(application)

    fun getAstronomyPictureOfTheDay(): LiveData<AstronomyPictureOfTheDay> {
        return astronomyPictureOfTheDay
    }

    fun submitDate(date: Date?) {
        date?.let {
            val liveApod = apodRepository.findByDate(date.formatDate())
            astronomyPictureOfTheDay.addSource(liveApod) { apod ->
                astronomyPictureOfTheDay.value = apod
            }
        }
    }

    fun downloadImage(url: String?) {
        val inputData = Data.Builder()
                .putString(IMAGE_URL, url)
                .build()
        val workRequest: WorkRequest = OneTimeWorkRequest
                .Builder(DownloadImageWorker::class.java)
                .setInputData(inputData)
                .build()
        workManager.enqueue(workRequest)
    }

}