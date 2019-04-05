package space.pal.sig.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;
import java.util.Random;

import space.pal.sig.R;
import space.pal.sig.util.Utils;

public class ImageActivity extends AppCompatActivity {

    private CoordinatorLayout coordinatorLayout;
    private ImageView imageView;
    private String hdurl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView toolbarTitle = findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getWindow().setStatusBarColor(getResources().getColor(android.R.color.transparent));
        imageView = findViewById(R.id.image);
        RelativeLayout content = findViewById(R.id.content);
        RelativeLayout noContent = findViewById(R.id.no_content);
        coordinatorLayout = findViewById(R.id.coordinator);
        ProgressBar progressBar = findViewById(R.id.progress_bar);
        String url = getIntent().getStringExtra("url");
        hdurl = getIntent().getStringExtra("hdurl");
        String str = getIntent().getStringExtra("title");
        toolbarTitle.setText(str);
        String hd = Utils.getFromSharedPreferences(Utils.QUALITY_HD, this, Utils.QUALITY_HD);
        boolean nohd = hd.equals("") || hd.equals("no");
        String localurl = nohd ? url : hdurl;
        boolean connected = Utils.isNetworkConnected(this);
        if (connected) {
            Glide.with(this)
                    .load(localurl)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(new SimpleTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull Drawable resource,
                                                    @Nullable Transition<? super Drawable> transition) {
                            imageView.setImageDrawable(resource);
                            progressBar.setVisibility(View.GONE);
                            content.setVisibility(View.VISIBLE);
                            toolbar.setVisibility(View.VISIBLE);
                            noContent.setVisibility(View.GONE);
                            Utils.customAnimation(ImageActivity.this, content,
                                    500, android.R.anim.fade_in);
                            if (savedInstanceState == null) notifyLandscape();
                        }

                        @Override
                        public void onLoadFailed(@Nullable Drawable errorDrawable) {
                            super.onLoadFailed(errorDrawable);
                            errorCall();
                            progressBar.setVisibility(View.GONE);
                            content.setVisibility(View.VISIBLE);
                            toolbar.setVisibility(View.VISIBLE);
                            noContent.setVisibility(View.GONE);
                            Utils.customAnimation(ImageActivity.this, content,
                                    500, android.R.anim.fade_in);
                        }
                    });
        } else {
            notifyInternet();
            progressBar.setVisibility(View.GONE);
            content.setVisibility(View.GONE);
            toolbar.setVisibility(View.GONE);
            noContent.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } else {
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void downloadImage() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            if (Utils.isNetworkConnected(this)) {
                Toast.makeText(this, "Downloading...", Toast.LENGTH_SHORT).show();
                new DownloadImage().execute(hdurl);
            }
            else {
                notifyInternet();
            }
        }
        else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, Utils.REQUEST_WRITE_PERMISSIONS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == Utils.REQUEST_WRITE_PERMISSIONS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (Utils.isNetworkConnected(this)) {
                    Toast.makeText(this, "Downloading...", Toast.LENGTH_SHORT).show();
                    new DownloadImage().execute(hdurl);
                }
                else {
                    notifyInternet();
                }
            } else {
                Toast.makeText(this, "Can`t download photo due to " +
                        "insufficient permissions! :(", Toast.LENGTH_LONG).show();
            }
        }
        else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.download, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.download:
                downloadImage();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class DownloadImage extends AsyncTask<String, Void, File> {

        @Override
        protected File doInBackground(String... urls) {
            Bitmap bitmap = null;

            HttpURLConnection httpURLConnection = null;

            try {
                URL url = new URL(urls[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                int status_code = httpURLConnection.getResponseCode();
                if (status_code != 200){
                    return null;
                }
                InputStream inputStream = httpURLConnection.getInputStream();
                if (inputStream != null){
                    bitmap = BitmapFactory.decodeStream(inputStream);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                if (httpURLConnection != null){
                    httpURLConnection.disconnect();
                }
            }
            return saveImageToGallery(bitmap);
        }

        @Override
        protected void onPostExecute(File file) {
            if (file != null) {
                Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                intent.setData(Uri.fromFile(file));
                sendBroadcast(intent);
                Toast.makeText(ImageActivity.this, "Download Complete!", Toast.LENGTH_SHORT).show();
            } else {
                errorCall();
            }
        }
    }

    private File saveImageToGallery(Bitmap bitmap){
        if (bitmap == null) {
            return null;
        }
        FileOutputStream outStream;
        File picturesDir = new File(Environment.getExternalStorageDirectory() + File.separator + "Pictures");
        File dir = new File(picturesDir.getAbsolutePath() + File.separator + "SpaceNow");
        boolean succes;
        if (!dir.exists()) {
            succes = dir.mkdir();
            if (succes) {
                Log.i("Directory", "Created");
            }
        }
        Random r = new Random();
        String name = "full_picture".concat(Integer.toString(r.nextInt()));
        String fileName = String.format("%s.jpg", name);
        File outFile = new File(dir, fileName);
        try {
            outStream = new FileOutputStream(outFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
            outStream.flush();
            outStream.close();
        } catch (IOException e ) {
            e.printStackTrace();
        }
        return outFile;
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    private void errorCall() {
        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, "Something has gone wrong. :(", 8000);
        snackbar.setActionTextColor(getResources().getColor(R.color.colorWhite));
        snackbar.setAction("Retry", view -> recreate());
        snackbar.show();
    }

    private void notifyInternet() {
        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, "No Internet Connection", 8000);
        snackbar.setActionTextColor(getResources().getColor(R.color.colorWhite));
        snackbar.setAction("Retry", view -> recreate());
        snackbar.show();
    }

    private void notifyLandscape() {
        Snackbar snackbar = Snackbar.make(coordinatorLayout, "Try landscape orientation " +
                "for wide images.", 2000);
        snackbar.show();
    }
}
