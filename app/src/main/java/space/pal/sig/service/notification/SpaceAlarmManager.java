package space.pal.sig.service.notification;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import static space.pal.sig.service.notification.SpaceAlarmReceiver.SPACE_ALARM_ACTION;

/**
 * SpaceNow
 * Created by Catalin on 7/21/2020
 **/
public class SpaceAlarmManager {

    public static void scheduleAlarm(Context context, long triggerAtMillis) {
        Intent intent = new Intent(SPACE_ALARM_ACTION);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0,
                intent, PendingIntent.FLAG_ONE_SHOT);
        AlarmManager manager = (AlarmManager) (context.getSystemService(Context.ALARM_SERVICE));
        manager.set(AlarmManager.ELAPSED_REALTIME, triggerAtMillis, pendingIntent);
    }

}
