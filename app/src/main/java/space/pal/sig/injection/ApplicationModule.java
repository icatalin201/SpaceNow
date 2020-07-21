package space.pal.sig.injection;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import lombok.RequiredArgsConstructor;

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
}
