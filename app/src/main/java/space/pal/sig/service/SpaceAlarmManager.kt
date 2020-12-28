package space.pal.sig.service

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent

/**
 * SpaceNow
 * Created by Catalin on 7/21/2020
 */
object SpaceAlarmManager {

    fun scheduleAlarm(context: Context, action: String?, triggerAtMillis: Long) {
        val intent = Intent(action)
        val pendingIntent = PendingIntent.getBroadcast(context, 0,
                intent, 0)
        val manager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        manager[AlarmManager.ELAPSED_REALTIME, triggerAtMillis] = pendingIntent
    }

    fun cancelAlarm(context: Context, action: String?) {
        val intent = Intent(action)
        val pendingIntent = PendingIntent.getBroadcast(context, 0,
                intent, PendingIntent.FLAG_CANCEL_CURRENT)
        val manager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        manager.cancel(pendingIntent)
    }
}