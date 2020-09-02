package space.pal.sig;

import android.app.Application;

import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import space.pal.sig.injection.ApiServiceModule;
import space.pal.sig.injection.ApplicationComponent;
import space.pal.sig.injection.ApplicationModule;
import space.pal.sig.injection.DaggerApplicationComponent;
import space.pal.sig.injection.DatabaseModule;
import space.pal.sig.service.notification.SpaceNotificationManager;

import static space.pal.sig.util.PicassoHelper.createBuilderWithTrustManager;

/**
 * SpaceNow
 * Created by Catalin on 7/17/2020
 **/
public class Space extends Application {

    private static ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        SpaceNotificationManager.createNotificationChannel(getApplicationContext());
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .apiServiceModule(new ApiServiceModule())
                .databaseModule(new DatabaseModule())
                .build();
        setupPicasso();
    }

    public static ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

    private void setupPicasso() {
        OkHttpClient okHttpClient = createBuilderWithTrustManager()
                .cache(new Cache(getCacheDir(), Integer.MAX_VALUE))
                .build();
        OkHttp3Downloader okHttpDownloader = new OkHttp3Downloader(okHttpClient);
        Picasso picasso = new Picasso.Builder(this)
                .downloader(okHttpDownloader)
                .loggingEnabled(true)
                .build();
        Picasso.setSingletonInstance(picasso);
    }
}
