package space.pal.sig.view.main

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import space.pal.sig.model.dto.IssPosition
import space.pal.sig.model.entity.AstronomyPictureOfTheDay
import space.pal.sig.model.entity.LaunchWithData
import space.pal.sig.model.entity.Roadster
import space.pal.sig.repository.ApodRepository
import space.pal.sig.repository.IssRepository
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
        issRepository: IssRepository,
        application: Application
) : BaseViewModel(application) {

    companion object {
        private const val ISS_POLL_PERIOD: Long = 35000
    }

    private val astronomyPictureOfTheDay = apodRepository.findLatest()
    private val nextLaunch = launchRepository.findNext()
    private val latestLaunch = launchRepository.findLatest()
    private val roadster = roadsterRepository.get()
    private val issPosition = MutableLiveData<IssPosition>()

    private val runnable = object : Runnable {
        override fun run() {
            val disposable = issRepository.getLiveLocation()
                    .subscribe({
                        issPosition.value = it.issPosition
                        mainHandler.postDelayed(this, ISS_POLL_PERIOD)
                    }, { it.printStackTrace() })
            compositeDisposable.add(disposable)
        }
    }

    override fun onResume() {
        super.onResume()
        mainHandler.post(runnable)
    }

    override fun onPause() {
        super.onPause()
        mainHandler.removeCallbacks(runnable)
    }

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

    fun getIssPosition(): LiveData<IssPosition> {
        return issPosition
    }

}