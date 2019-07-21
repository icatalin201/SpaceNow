package space.pal.sig;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import space.pal.sig.di.ApiServiceModule;
import space.pal.sig.di.ApplicationComponent;
import space.pal.sig.di.ContextModule;
import space.pal.sig.di.DaggerApplicationComponent;
import space.pal.sig.util.Shared;

public class Space extends Application {

    private static ApplicationComponent applicationComponent;
    private static final String CHANNEL_ID = "space_now_channel_01";

    @Override
    public void onCreate() {
        super.onCreate();
        Shared.init(getApplicationContext());
        createNotificationChannel(getApplicationContext());
        applicationComponent = DaggerApplicationComponent.builder()
                .contextModule(new ContextModule(getApplicationContext()))
                .apiServiceModule(new ApiServiceModule())
                .build();
    }

    public static ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

    private void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Space Now";
            String description = "Space Now Notification Channel";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }
}
