package space.pal.sig.view.launches

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import space.pal.sig.model.Launch
import space.pal.sig.repository.LaunchRepository
import space.pal.sig.view.BaseViewModel

/**
 * SpaceNow
 * Created by Catalin on 12/23/2020
 **/
class LaunchesViewModel(
        private val launchRepository: LaunchRepository
) : BaseViewModel() {

    private val launches = MutableLiveData<MutableList<Launch>>()

    init {
        fetchLaunches()
    }

    fun getLaunches(): LiveData<MutableList<Launch>> {
        return launches
    }

    private fun fetchLaunches() {
        val disposable = launchRepository
                .getUpcomingLaunches()
                .subscribe({ launches.value = it }, { it.printStackTrace() })
        compositeDisposable.add(disposable)
    }

}