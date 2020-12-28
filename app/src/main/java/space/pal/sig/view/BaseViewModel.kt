package space.pal.sig.view

import android.app.Application
import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import androidx.lifecycle.AndroidViewModel
import io.reactivex.disposables.CompositeDisposable

/**
SpaceNow
Created by Catalin on 12/13/2020
 **/
open class BaseViewModel(
        application: Application
) : AndroidViewModel(application) {

    protected val handler: Handler
    protected val mainHandler = Handler(Looper.getMainLooper())
    protected val compositeDisposable = CompositeDisposable()

    init {
        val mHandlerThread = HandlerThread("BaseViewModelHandlerThread")
        mHandlerThread.start()
        handler = Handler(mHandlerThread.looper)
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

    open fun onResume() {}

    open fun onPause() {}

}