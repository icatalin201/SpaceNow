package space.pal.sig.view.launch

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import space.pal.sig.model.entity.CrewMember
import space.pal.sig.model.entity.LaunchWithData
import space.pal.sig.repository.CrewMemberRepository
import space.pal.sig.repository.LaunchRepository
import space.pal.sig.util.SharedPreferencesUtil
import space.pal.sig.view.BaseViewModel

/**
 * SpaceNow
 * Created by Catalin on 12/25/2020
 **/
class LaunchViewModel(
        application: Application,
        private val launchRepository: LaunchRepository,
        private val sharedPreferencesUtil: SharedPreferencesUtil,
        private val crewMemberRepository: CrewMemberRepository
) : BaseViewModel(application) {

    companion object {
        private const val IMAGE_CHANGE_DELAY: Long = 15000
        private const val LAUNCH_NOTIFICATIONS = "notifications"
    }

    private val launch = MediatorLiveData<LaunchWithData>()
    private val crew = MediatorLiveData<List<CrewMember>>()
    private val currentRocketImage = MutableLiveData<String>()
    private val currentLaunchImage = MutableLiveData<String>()
    private val rocketImages: MutableList<String> = mutableListOf()
    private val launchImages: MutableList<String> = mutableListOf()

    private val runnable = object : Runnable {
        override fun run() {
            var rocketIndex = rocketImages.indexOf(currentRocketImage.value) + 1
            var launchIndex = launchImages.indexOf(currentLaunchImage.value) + 1
            if (rocketIndex == rocketImages.size) rocketIndex = 0
            if (launchIndex == launchImages.size) launchIndex = 0
            currentRocketImage.value = rocketImages[rocketIndex]
            currentLaunchImage.value = launchImages[launchIndex]
            mainHandler.postDelayed(this, IMAGE_CHANGE_DELAY)
        }
    }

    override fun onCleared() {
        mainHandler.removeCallbacks(runnable)
        super.onCleared()
    }

    fun toggleNotifications(isOn: Boolean, launchId: String?) {
        val notifications: MutableSet<String> = sharedPreferencesUtil.get(LAUNCH_NOTIFICATIONS)
        if (isOn) {
            notifications.add(launchId.toString())
        } else {
            notifications.remove(launchId.toString())
        }
        sharedPreferencesUtil.save(LAUNCH_NOTIFICATIONS, notifications)
    }

    fun hasNotifications(launchId: String?): Boolean {
        return sharedPreferencesUtil.get(LAUNCH_NOTIFICATIONS).contains(launchId.toString())
    }

    fun submitLaunchId(launchId: String?) {
        launchId?.let { id ->
            val liveLaunch = launchRepository.findById(id)
            launch.addSource(liveLaunch) { launch ->
                this.launch.value = launch
                val liveCrewData = crewMemberRepository.findAllById(launch.launch.crewIds)
                crew.addSource(liveCrewData) { this.crew.value = it }
                launch.launch.links.flickr.original?.let {
                    launchImages.addAll(it)
                }
                launch.rocket?.let { rocket ->
                    rocketImages.addAll(rocket.images)
                    if (launchImages.isEmpty()) {
                        launchImages.addAll(rocket.images)
                    }
                }
                mainHandler.post(runnable)
            }
        }
    }

    fun getLaunch(): LiveData<LaunchWithData> {
        return launch
    }

    fun getCrew(): LiveData<List<CrewMember>> {
        return crew
    }

    fun getCurrentRocketImage(): LiveData<String> {
        return currentRocketImage
    }

    fun getCurrentLaunchImage(): LiveData<String> {
        return currentLaunchImage
    }

}