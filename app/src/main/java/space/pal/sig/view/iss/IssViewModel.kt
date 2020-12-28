package space.pal.sig.view.iss

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import space.pal.sig.model.dto.IssPass
import space.pal.sig.model.dto.IssPosition
import space.pal.sig.repository.IssRepository
import space.pal.sig.view.BaseViewModel

/**
 * SpaceNow
 * Created by Catalin on 12/28/2020
 **/
class IssViewModel(
        private val issRepository: IssRepository,
        application: Application
) : BaseViewModel(application) {

    companion object {
        private const val ISS_POLL_PERIOD: Long = 5000
    }

    private val issPosition = MutableLiveData<IssPosition>()
    private val issPassList = MutableLiveData<List<IssPass>>()

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

    fun askForIssPass(latitude: Double, longitude: Double) {
        val disposable = issRepository.getPassTimes(latitude, longitude)
                .subscribe({ issPassList.value = it.response }, { it.printStackTrace() })
        compositeDisposable.add(disposable)
    }

    fun getIssPosition(): LiveData<IssPosition> {
        return issPosition
    }

    fun getIssPassList(): LiveData<List<IssPass>> {
        return issPassList
    }

}