package space.pal.sig.view.launch

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
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

    private val launch = MediatorLiveData<LaunchWithData>()

    fun submitLaunchId(launchId: String?) {
        launchId?.let {
            val liveLaunch = launchRepository.findLaunch(it)
            launch.addSource(liveLaunch) { launch ->
                this.launch.value = launch
            }
        }
    }

    fun getLaunch(): LiveData<LaunchWithData> {
        return launch
    }

}