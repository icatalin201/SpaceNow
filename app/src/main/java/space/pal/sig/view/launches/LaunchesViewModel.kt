package space.pal.sig.view.launches

import android.app.Application
import androidx.lifecycle.LiveData
import space.pal.sig.model.entity.Launch
import space.pal.sig.model.entity.LaunchWithData
import space.pal.sig.repository.LaunchRepository
import space.pal.sig.view.BaseViewModel

/**
 * SpaceNow
 * Created by Catalin on 12/23/2020
 **/
class LaunchesViewModel(
        private val launchRepository: LaunchRepository,
        application: Application
) : BaseViewModel(application) {

    private val launches = launchRepository.findUpcomingLaunches()

    fun getLaunches(): LiveData<MutableList<LaunchWithData>> {
        return launches
    }

}