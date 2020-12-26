package space.pal.sig.view.launch

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import space.pal.sig.model.entity.LaunchWithData
import space.pal.sig.repository.LaunchRepository
import space.pal.sig.view.BaseViewModel

/**
 * SpaceNow
 * Created by Catalin on 12/25/2020
 **/
class LaunchViewModel(
        application: Application,
        private val launchRepository: LaunchRepository
) : BaseViewModel(application) {

    companion object {
        private const val IMAGE_CHANGE_DELAY: Long = 15000
    }

    private val launch = MediatorLiveData<LaunchWithData>()
    private val currentImage = MutableLiveData<String>()
    private val images: MutableList<String> = mutableListOf()

    private val runnable = object : Runnable {
        override fun run() {
            var index = images.indexOf(currentImage.value) + 1
            if (index == images.size) index = 0
            currentImage.value = images[index]
            mainHandler.postDelayed(this, IMAGE_CHANGE_DELAY)
        }
    }

    override fun onCleared() {
        mainHandler.removeCallbacks(runnable)
        super.onCleared()
    }

    fun submitLaunchId(launchId: String?) {
        launchId?.let { id ->
            val liveLaunch = launchRepository.findById(id)
            launch.addSource(liveLaunch) { launch ->
                this.launch.value = launch
                launch.rocket?.let { rocket ->
                    this.images.addAll(rocket.images)
                }
                mainHandler.post(runnable)
            }
        }
    }

    fun getLaunch(): LiveData<LaunchWithData> {
        return launch
    }

    fun getCurrentImage(): LiveData<String> {
        return currentImage
    }

}