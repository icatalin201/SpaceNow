package space.pal.sig.di;

import javax.inject.Singleton;

import dagger.Component;
import space.pal.sig.view.activity.ApodActivity;
import space.pal.sig.view.activity.FeedActivity;
import space.pal.sig.view.activity.MainActivity;
import space.pal.sig.view.activity.NewsActivity;
import space.pal.sig.view.activity.SplashActivity;

@Singleton
@Component(modules = {
        ApiServiceModule.class,
        ContextModule.class,
        DatabaseModule.class,
        ViewModelModule.class
})
public interface ApplicationComponent {
    void inject(MainActivity activity);
    void inject(SplashActivity activity);
    void inject(ApodActivity activity);
    void inject(NewsActivity activity);
    void inject(FeedActivity activity);
}
