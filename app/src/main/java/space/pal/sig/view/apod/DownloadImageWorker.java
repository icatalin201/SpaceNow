package space.pal.sig.view.apod;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;

import space.pal.sig.service.notification.SpaceNotificationManager;

import static space.pal.sig.view.apod.ImageViewModel.IMAGE_URL;

/**
 * SpaceNow
 * Created by Catalin on 7/28/2020
 **/
public class DownloadImageWorker extends Worker {

    private final Context context;

    public DownloadImageWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
    }

    @NonNull
    @Override
    public Result doWork() {
        int notificationId = new SecureRandom().nextInt();
        SpaceNotificationManager.createNotification(context,
                "Downloading image...", notificationId);
        Data data = getInputData();
        String url = data.getString(IMAGE_URL);
        try {
            Bitmap bitmap = downloadFile(url);
            saveImageToGallery(bitmap);
            SpaceNotificationManager.createBigNotification(context,
                    "Image downloaded", notificationId, bitmap);
        } catch (IOException e) {
            e.printStackTrace();
            SpaceNotificationManager.createNotification(context,
                    "Download failed", notificationId);
            return Result.failure();
        }
        return Result.success();
    }

    private Bitmap downloadFile(String urlString) throws IOException {
        Bitmap bitmap = null;
        URL url = new URL(urlString);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        int statusCode = httpURLConnection.getResponseCode();
        if (statusCode == 200) {
            InputStream inputStream = httpURLConnection.getInputStream();
            if (inputStream != null) {
                bitmap = BitmapFactory.decodeStream(inputStream);
            }
        }
        httpURLConnection.disconnect();
        return bitmap;
    }

    private void saveImageToGallery(Bitmap bitmap) throws IOException {
        String name = String.valueOf(System.currentTimeMillis());
        String fileName = String.format("%s.jpg", name);
        File image = new File(context.getExternalFilesDir(
                Environment.DIRECTORY_PICTURES), fileName);
        OutputStream outStream = new FileOutputStream(image);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
        outStream.flush();
        outStream.close();
        refreshGallery(image);
    }

    private void refreshGallery(File file) {
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intent.setData(Uri.fromFile(file));
        context.sendBroadcast(intent);
    }
}
