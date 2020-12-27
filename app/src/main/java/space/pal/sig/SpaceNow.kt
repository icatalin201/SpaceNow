package space.pal.sig

import android.app.Application
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import okhttp3.Cache
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import space.pal.sig.service.notification.SpaceNotificationManager.createNotificationChannel
import space.pal.sig.util.InjectionModule
import space.pal.sig.util.OkHttpBuilderExtensions.setupTrustManager

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
        setupPicasso()
        createNotificationChannel(applicationContext)
    }

    private fun setupPicasso() {
        val okHttpClient: OkHttpClient = OkHttpClient.Builder()
                .setupTrustManager()
                .cache(Cache(cacheDir, Int.MAX_VALUE.toLong()))
                .build()
        val okHttpDownloader = OkHttp3Downloader(okHttpClient)
        val picasso = Picasso.Builder(this)
                .downloader(okHttpDownloader)
                .loggingEnabled(false)
                .build()
        Picasso.setSingletonInstance(picasso)
    }

}
