package space.pal.sig.view.splash

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import space.pal.sig.service.DataSyncWorker
import space.pal.sig.service.ScheduleManager
import space.pal.sig.util.SharedPreferencesUtil
import space.pal.sig.view.BaseViewModel

/**
SpaceNow
Created by Catalin on 12/13/2020
 **/
class SplashViewModel(
        application: Application,
        sharedPreferencesUtil: SharedPreferencesUtil
) : BaseViewModel(application) {

    companion object {
        const val IS_FIRST_TIME = "SpaceNow.IsFirstTime"
    }

    private val safeForStart = MediatorLiveData<Boolean>()
    private val syncResult = ScheduleManager
            .launchOneTimeSync(application, DataSyncWorker.DATA_SYNC_WORKER_TAG)

    init {
        val isFirstTime = sharedPreferencesUtil.get(IS_FIRST_TIME, true)
        if (isFirstTime) {
            safeForStart.addSource(syncResult) { workInfoList ->
                val finished = workInfoList[0].state.isFinished
                safeForStart.value = finished
                sharedPreferencesUtil.save(IS_FIRST_TIME, false)
            }
        } else {
            safeForStart.value = true
        }
    }

    fun isSafeForStart(): LiveData<Boolean> {
        return safeForStart
    }

}