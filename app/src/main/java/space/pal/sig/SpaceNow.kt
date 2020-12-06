package space.pal.sig

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import space.pal.sig.util.InjectionModule

/**
SpaceNow
Created by Catalin on 12/6/2020
 **/
class SpaceNow : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@SpaceNow)
            modules(InjectionModule.appModule)
        }
    }

}