package space.pal.sig.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * SpaceNow
 * Created by Catalin on 7/21/2020
 **/
public class SpaceAlarmReceiver extends BroadcastReceiver {

    public static final String SPACE_ALARM_ACTION = "SPACE.ALARM.ACTION";

    @Override
    public void onReceive(Context context, Intent intent) {
        SpaceNotificationManager.createNotification(context, "Testare");
    }
}
