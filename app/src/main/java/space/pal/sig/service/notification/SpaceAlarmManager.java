package space.pal.sig.service.notification;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

/**
 * SpaceNow
 * Created by Catalin on 7/21/2020
 **/
public class SpaceAlarmManager {

    public static void scheduleAlarm(Context context, String action, long triggerAtMillis) {
        Intent intent = new Intent(action);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0,
                intent, 0);
        AlarmManager manager = (AlarmManager) (context.getSystemService(Context.ALARM_SERVICE));
        manager.set(AlarmManager.ELAPSED_REALTIME, triggerAtMillis, pendingIntent);
    }

    public static void cancelAlarm(Context context, String action) {
        Intent intent = new Intent(action);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0,
                intent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager manager = (AlarmManager) (context.getSystemService(Context.ALARM_SERVICE));
        manager.cancel(pendingIntent);
    }

}
