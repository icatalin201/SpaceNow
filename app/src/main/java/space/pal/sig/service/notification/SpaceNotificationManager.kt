package space.pal.sig.service.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import androidx.core.app.NotificationCompat
import space.pal.sig.R
import space.pal.sig.view.main.MainActivity
import java.security.SecureRandom

/**
 * SpaceNow
 * Created by Catalin on 7/17/2020
 */
object SpaceNotificationManager {

    const val NOTIFICATION_CHANNEL_ID = "space_now_channel_01"
    private const val CHANNEL_NAME = "Space Now"
    private const val CHANNEL_DESCRIPTION = "Space Now - The Space at your fingertips"

    @JvmStatic
    fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(NOTIFICATION_CHANNEL_ID, CHANNEL_NAME, importance)
            channel.description = CHANNEL_DESCRIPTION
            val notificationManager = context.getSystemService(NotificationManager::class.java)
            notificationManager?.createNotificationChannel(channel)
        }
    }

    fun createBigNotification(context: Context, text: String?, id: Int, bitmap: Bitmap?) {
        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val notification = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
                .setContentTitle(context.getString(R.string.app_name))
                .setContentText(text)
                .setSmallIcon(R.drawable.ic_icons8_launchpad)
                .setContentIntent(pendingIntent)
                .setLargeIcon(bitmap)
                .setStyle(NotificationCompat.BigPictureStyle().bigPicture(bitmap))
                .setAutoCancel(true)
                .build()
        val notificationManager = context
                .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(id, notification)
    }

    fun createBigNotification(context: Context, text: String?, bitmap: Bitmap?) {
        createBigNotification(context, text, SecureRandom().nextInt(), bitmap)
    }

    @JvmStatic
    @JvmOverloads
    fun createNotification(context: Context, text: String?, id: Int = SecureRandom().nextInt()) {
        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val notification = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
                .setContentTitle(context.getString(R.string.app_name))
                .setContentText(text)
                .setSmallIcon(R.drawable.ic_icons8_launchpad)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build()
        val notificationManager = context
                .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(id, notification)
    }
}