package space.pal.sig.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
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
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import space.pal.sig.R;
import space.pal.sig.activity.ImageActivity;
import space.pal.sig.model.Apod;
import space.pal.sig.network.Client;
import space.pal.sig.network.Service;
import space.pal.sig.util.SqlService;
import space.pal.sig.util.Utils;

public class ApodFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    private ProgressBar progressBar;
    private LinearLayout apodLayout;
    private ImageView apodImage;
    private TextView apodDescription;
    private TextView apodTitle;
    private TextView apodDate;
    private TextView apodCredits;
    private Context context;
    private Apod apod;
    private CoordinatorLayout coordinatorLayout;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RelativeLayout relativeLayout;

    private RelativeLayout imageContent;
    private WebView videoContent;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_apod, container, false);
        progressBar = view.findViewById(R.id.progress_bar);
        apodLayout = view.findViewById(R.id.apod_layout);
        apodImage = view.findViewById(R.id.apod_image);
        apodDescription = view.findViewById(R.id.apod_description);
        coordinatorLayout = view.findViewById(R.id.coordinator);
        apodDate = view.findViewById(R.id.apod_date);
        apodCredits = view.findViewById(R.id.apod_credits);
        apodTitle = view.findViewById(R.id.apod_title);
        imageContent = view.findViewById(R.id.image_type);
        videoContent = view.findViewById(R.id.video);
        relativeLayout = view.findViewById(R.id.no_content);
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(this);
        apodImage.setOnClickListener(this);
        ImageButton downloadButton = view.findViewById(R.id.download);
        downloadButton.setOnClickListener(view1 -> downloadImage());
        context = getActivity();
        if (context != null) {
            Objects.requireNonNull(((AppCompatActivity) context).getSupportActionBar())
                    .setTitle(getString(R.string.apod));
            refresh();
        }
        return view;
    }

    private void refresh() {
        boolean connected = Utils.isNetworkConnected(context);
        if (connected) {
            startRequestForAPOD();
        }
        else {
            Drawable drawable = context.getResources().getDrawable(R.drawable.ic_placeholder);
            drawable.setAlpha(110);
            Glide.with(context).load(drawable).into(apodImage);
            notifyInternet();
            progressBar.setVisibility(View.GONE);
            apodLayout.setVisibility(View.GONE);
            relativeLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    private void notifyInternet() {
        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, "No Internet Connection", 8000);
        snackbar.setActionTextColor(context.getResources().getColor(R.color.colorWhite));
        snackbar.setAction("Retry", view -> ((AppCompatActivity) context).recreate());
        snackbar.show();
    }

    private void errorApodCall() {
        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, "Something has gone wrong. :(", 8000);
        snackbar.setActionTextColor(context.getResources().getColor(R.color.colorWhite));
        snackbar.setAction("Retry", view -> ((AppCompatActivity) context).recreate());
        snackbar.show();
    }

    private void downloadImage() {
        if (ActivityCompat.checkSelfPermission(context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            if (Utils.isNetworkConnected(context)) {
                if (apod != null) {
                    Toast.makeText(context, "Downloading...", Toast.LENGTH_SHORT).show();
                    new DownloadImage().execute(apod.getHdurl());
                }
                else {
                    errorApodCall();
                }
            }
            else {
                notifyInternet();
            }
        }
        else {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    Utils.REQUEST_WRITE_PERMISSIONS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == Utils.REQUEST_WRITE_PERMISSIONS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (Utils.isNetworkConnected(context)) {
                    if (apod != null) {
                        Toast.makeText(context, "Downloading...", Toast.LENGTH_SHORT).show();
                        new DownloadImage().execute(apod.getHdurl());
                    }
                    else {
                        errorApodCall();
                    }
                }
                else {
                    notifyInternet();
                }
            } else {
                Toast.makeText(context, "Can`t download photo due to " +
                        "insufficient permissions! :(", Toast.LENGTH_LONG).show();
            }
        }
        else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void startRequestForAPOD() {
        Service service = Client.getRetrofitClient(Utils.NASA_URL).create(Service.class);
        Call<Apod> apodCall = service.getAstronomyPictureOfTheDay(Utils.API_KEY);
        apodCall.enqueue(new Callback<Apod>() {
            @SuppressLint("SetJavaScriptEnabled")
            @Override
            public void onResponse(Call<Apod> call, Response<Apod> response) {
                if (response.isSuccessful() && response.body() != null) {
                    apod = response.body();
                    try {
                        String date = Utils.transformDateFormat(apod.getDate(), Utils.DATE_FORMAT, Utils.LOCAL_DATE_FORMAT);
                        final Drawable drawable = context.getResources().getDrawable(R.drawable.ic_placeholder);
                        drawable.setAlpha(110);
                        apodDescription.setText(apod.getExplanation());
                        apodTitle.setText(apod.getTitle());
                        apodDate.setText(String.format("Date: %s" , date));
                        apodCredits.setText(String.format("Credits: %s", apod.getCopyright()));
                        if (apod.getMedia_type().equals("image")) {
                            imageContent.setVisibility(View.VISIBLE);
                            videoContent.setVisibility(View.GONE);
                            Glide.with(context)
                                    .load(apod.getUrl())
                                    .apply(new RequestOptions()
                                            .placeholder(drawable)
                                            .error(drawable))
                                    .into(new SimpleTarget<Drawable>() {
                                        @Override
                                        public void onResourceReady(@NonNull Drawable resource,
                                                                    @Nullable Transition<? super Drawable> transition) {
                                            apodImage.setImageDrawable(resource);
                                            progressBar.setVisibility(View.GONE);
                                            apodLayout.setVisibility(View.VISIBLE);
                                            relativeLayout.setVisibility(View.GONE);
                                            Utils.customAnimation(context, apodLayout, 500, android.R.anim.fade_in);
                                        }
                                    });
                            SqlService.insertApod(context, apod);
                        } else {
                            videoContent.setVisibility(View.VISIBLE);
                            imageContent.setVisibility(View.GONE);
                            videoContent.loadUrl(apod.getUrl());
                            videoContent.getSettings().setJavaScriptEnabled(true);
                            progressBar.setVisibility(View.GONE);
                            apodLayout.setVisibility(View.VISIBLE);
                            relativeLayout.setVisibility(View.GONE);
                            Utils.customAnimation(context, apodLayout, 500, android.R.anim.fade_in);
                        }

                    } catch (ParseException e) {
                        e.printStackTrace();
                        errorApodCall();
                    }
                }
                else {
                    Log.e("CALL_ERROR", "Error: " + response.message());
                    errorApodCall();
                }
            }

            @Override
            public void onFailure(Call<Apod> call, Throwable t) {
                Log.e("CALL_ERROR", "Error: " + t.getMessage());
                errorApodCall();
            }
        });
    }

    @Override
    public void onRefresh() {
        refresh();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(context, ImageActivity.class);
        intent.putExtra("hdurl", apod.getHdurl());
        intent.putExtra("url", apod.getUrl());
        intent.putExtra("title", apod.getTitle());
        context.startActivity(intent);
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
                    bitmap = ((BitmapDrawable) apodImage.getDrawable()).getBitmap();
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
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            intent.setData(Uri.fromFile(file));
            context.sendBroadcast(intent);
            Toast.makeText(context, "Download Complete!", Toast.LENGTH_SHORT).show();
        }
    }

    private File saveImageToGallery(Bitmap bitmap){
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
        String name = apodTitle.getText().toString()
                .replace(" ", "")
                .concat(Integer.toString(r.nextInt()));
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
}
