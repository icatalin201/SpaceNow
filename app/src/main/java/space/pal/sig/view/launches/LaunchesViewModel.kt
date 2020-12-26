package space.pal.sig.view.launches

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import space.pal.sig.R
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

    private val launches = MediatorLiveData<MutableList<LaunchWithData>>()
    private val selectedOption = MutableLiveData<String>()
    private val options = arrayOf(
            application.getString(R.string.upcoming_launches),
            application.getString(R.string.past_launches),
    )
    private var launchesLiveData: LiveData<MutableList<LaunchWithData>>? = null

    init {
        filter(0)
    }

    fun getLaunches(): LiveData<MutableList<LaunchWithData>> {
        return launches
    }

    fun getSelectedOption(): LiveData<String> {
        return selectedOption
    }

    fun getOptions(): Array<String> {
        return options
    }

    fun filter(pos: Int) {
        when (pos) {
            0 -> {
                findFutureLaunches()
            }
            1 -> {
                findPastLaunches()
            }
        }
        selectedOption.value = options[pos]
    }

    private fun findFutureLaunches() {
        launchesLiveData?.let { launches.removeSource(it) }
        launchesLiveData = launchRepository.findAllFromFuture()
        launchesLiveData?.let {
            launches.addSource(it) { data ->
                launches.value = data
            }
        }
    }

    private fun findPastLaunches() {
        launchesLiveData?.let { launches.removeSource(it) }
        launchesLiveData = launchRepository.findAllFromPast()
        launchesLiveData?.let {
            launches.addSource(it) { data ->
                launches.value = data
            }
        }
    }

}