package space.pal.sig.view

import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import androidx.lifecycle.ViewModel

/**
SpaceNow
Created by Catalin on 12/13/2020
 **/
open class BaseViewModel : ViewModel() {

    protected val handler: Handler
    protected val mainHandler = Handler(Looper.getMainLooper())

    init {
        val mHandlerThread = HandlerThread("BaseViewModelHandlerThread")
        mHandlerThread.start()
        handler = Handler(mHandlerThread.looper)
    }

}