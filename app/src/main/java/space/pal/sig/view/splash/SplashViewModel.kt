package space.pal.sig.view.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import space.pal.sig.view.BaseViewModel

/**
SpaceNow
Created by Catalin on 12/13/2020
 **/
class SplashViewModel : BaseViewModel() {

    private val safeForStart = MutableLiveData<Boolean>()

    init {
        mainHandler.postDelayed({ safeForStart.value = true }, 1000)
    }

    fun isSafeForStart(): LiveData<Boolean> {
        return safeForStart
    }

}