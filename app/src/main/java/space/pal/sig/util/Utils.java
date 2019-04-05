package space.pal.sig.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import space.pal.sig.R;

import static android.content.Context.MODE_PRIVATE;

public class Utils {

    public static final String HUBBLE_URL = "http://hubblesite.org";
    public static final String NEWS = "/api/v3/news";
    public static final String NEWS_RELEASE = "/api/v3/news_release/{news_id}";
    public static final String NEWS_RELEASE_LAST = "/api/v3/news_release/last";
    public static final String IMAGES = "/api/v3/images/";
    public static final String IMAGES_MULTI = "/api/v3/images/{keyword}";
    public static final String IMAGE_DETAILS = "/api/v3/image/{image_id}";
    public static final String GLOSSARY = "/api/v3/glossary/{keyword}";
    public static final String GLOSSARY_ALL = "/api/v3/glossary/";
    public static final String FEED = "/api/v3/external_feed/";
    public static final String VIDEOS = "/api/v3/videos";
    public static final String VIDEO = "/api/v3/video/{video_id}";

    public static final String ESA_FEED = "esa_feed";
    public static final String JWST_FEED = "jwst_feed";
    public static final String ST_LIVE_FEED = "st_live";

    public static final String API_KEY = "BST2Npgb7FKgBIVzIW2Dd3qARcQbwgk1jdY5Hc28";
    public static final String NASA_URL = "https://api.nasa.gov";
    public static final String APOD = "/planetary/apod";

    public static final String VIEW_TYPE = "view_type";
    public static final String CARD_VIEW = "card_view";
    public static final String LIST_VIEW = "list_view";

    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String LOCAL_DATE_FORMAT = "dd MMMM yyyy";

    public static final String FIRST_TIME_FLAG = "first_time";

    public static final int REQUEST_WRITE_PERMISSIONS = 123;

    static final String CHANNEL_ID = "space_now_channel_01";
    public static final String NOTIFICATION = "notification";
    public static final String QUALITY_HD = "hd";

    public static void customAnimation(Context context, View view, int duration, int anim) {
        Animation animation = AnimationUtils.loadAnimation(context, anim);
        animation.setDuration(duration);
        view.startAnimation(animation);
    }

    public static void changeFragment(Context context, Fragment fragment) {
        FragmentManager fragmentManager = ((AppCompatActivity) context)
                .getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.FragmentContainer, fragment).commit();
    }

    public static void changeFragmentWithArguments(Context context, Fragment fragment, Bundle args) {
        FragmentManager fragmentManager = ((AppCompatActivity) context)
                .getSupportFragmentManager();
        fragment.setArguments(args);
        fragmentManager.beginTransaction().replace(R.id.FragmentContainer, fragment).commit();
    }

    public static void putInSharedPreferences(String pref_name, Context context, String key, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(pref_name, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getFromSharedPreferences(String pref_name, Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(pref_name, MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = null;
        if (connectivityManager != null) {
            activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        }
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static String getDate(String format) {
        return new SimpleDateFormat(format, Locale.getDefault())
                .format(Calendar.getInstance().getTime());
    }

    public static String transformDateFormat(String dateString, String sourceFormat, String targetFormat) throws ParseException {
        SimpleDateFormat sourceDateFormat = new SimpleDateFormat(sourceFormat, Locale.getDefault());
        Date date = sourceDateFormat.parse(dateString);
        return new SimpleDateFormat(targetFormat, Locale.getDefault()).format(date);
    }
}
