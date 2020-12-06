package space.pal.sig.old.injection;

import android.app.Application;
import android.content.Context;

import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import lombok.RequiredArgsConstructor;
import space.pal.sig.old.util.SharedPreferencesUtil;
import space.pal.sig.old.util.SpaceExecutors;

@Module
@RequiredArgsConstructor
public class ApplicationModule {

    private final Application application;

    @Provides
    @Singleton
    Application application() {
        return application;
    }

    @Provides
    @Singleton
    Context context() {
        return application.getApplicationContext();
    }

    @Provides
    @Singleton
    SharedPreferencesUtil sharedPreferencesUtil(Context context, Gson gson) {
        return new SharedPreferencesUtil(context, gson);
    }

    @Provides
    @Singleton
    SpaceExecutors spaceExecutors() {
        return new SpaceExecutors();
    }
}
