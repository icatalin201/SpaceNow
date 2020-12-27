package space.pal.sig.service.download

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import androidx.work.Worker
import androidx.work.WorkerParameters
import space.pal.sig.service.notification.SpaceNotificationManager.createBigNotification
import space.pal.sig.service.notification.SpaceNotificationManager.createNotification
import space.pal.sig.view.apod.ApodViewModel.Companion.IMAGE_URL
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.net.HttpURLConnection
import java.net.URL
import java.security.SecureRandom

/**
SpaceNow
Created by Catalin on 12/27/2020
 **/
class DownloadImageWorker(
        context: Context,
        workerParameters: WorkerParameters
) : Worker(context, workerParameters) {

    override fun doWork(): Result {
        val notificationId = SecureRandom().nextInt()
        createNotification(applicationContext,
                "Downloading image...", notificationId)
        val data = inputData
        val url = data.getString(IMAGE_URL)
        try {
            url?.let {
                val bitmap: Bitmap? = downloadFile(url)
                bitmap?.let {
                    saveImageToGallery(bitmap)
                    createBigNotification(applicationContext,
                            "Image downloaded", notificationId, bitmap)
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
            createNotification(applicationContext,
                    "Download failed", notificationId)
            return Result.failure()
        }
        return Result.success()
    }

    @Throws(IOException::class)
    private fun downloadFile(urlString: String): Bitmap? {
        var bitmap: Bitmap? = null
        val url = URL(urlString)
        val httpURLConnection = url.openConnection() as HttpURLConnection
        val statusCode = httpURLConnection.responseCode
        if (statusCode == 200) {
            val inputStream = httpURLConnection.inputStream
            if (inputStream != null) {
                bitmap = BitmapFactory.decodeStream(inputStream)
            }
        }
        httpURLConnection.disconnect()
        return bitmap
    }

    @Throws(IOException::class)
    private fun saveImageToGallery(bitmap: Bitmap) {
        val name = System.currentTimeMillis().toString()
        val fileName = String.format("%s.jpg", name)
        val image = File(applicationContext.getExternalFilesDir(
                Environment.DIRECTORY_PICTURES), fileName)
        val outStream: OutputStream = FileOutputStream(image)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream)
        outStream.flush()
        outStream.close()
        refreshGallery(image)
    }

    private fun refreshGallery(file: File) {
        val intent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
        intent.data = Uri.fromFile(file)
        applicationContext.sendBroadcast(intent)
    }
}