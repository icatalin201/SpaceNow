package space.pal.sig.service.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import java.security.SecureRandom;

import space.pal.sig.R;
import space.pal.sig.view.main.MainActivity;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * SpaceNow
 * Created by Catalin on 7/17/2020
 **/
public class SpaceNotificationManager {

    public static final String NOTIFICATION_CHANNEL_ID = "space_now_channel_01";
    private static final String CHANNEL_NAME = "Space Now";
    private static final String CHANNEL_DESCRIPTION = "Space Now - The Space at your fingertips";

    public static void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel =
                    new NotificationChannel(NOTIFICATION_CHANNEL_ID, CHANNEL_NAME, importance);
            channel.setDescription(CHANNEL_DESCRIPTION);
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

    public static void createNotification(Context context, String title) {
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = new NotificationCompat
                .Builder(context, NOTIFICATION_CHANNEL_ID)
                .setContentTitle(title)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();
        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(new SecureRandom().nextInt(), notification);
    }
}
