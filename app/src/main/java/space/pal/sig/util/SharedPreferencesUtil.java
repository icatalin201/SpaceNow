package space.pal.sig.util;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.util.HashSet;
import java.util.Set;

/**
 * SpaceNow
 * Created by Catalin on 7/22/2020
 **/
public class SharedPreferencesUtil {

    private static final String SPACE_PREFERENCES = "Space.Shared.Preferences";
    private final SharedPreferences preferences;
    private final Gson gson;

    public SharedPreferencesUtil(Context context, Gson gson) {
        this.preferences = PreferenceManager.getDefaultSharedPreferences(context);
        this.gson = gson;
    }

    public void storeInteger(String key, Integer value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public void storeBoolean(String key, Boolean value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public void storeLong(String key, Long value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    public void storeFloat(String key, Float value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putFloat(key, value);
        editor.apply();
    }

    public void storeString(String key, String value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public void storeSet(String key, Set<String> items) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putStringSet(key, items);
        editor.apply();
    }

    public void storeObject(String key, Object object) {
        storeString(key, gson.toJson(object));
    }

    public Integer getInteger(String key, Integer defaultValue) {
        return preferences.getInt(key, defaultValue);
    }

    public Boolean getBoolean(String key, Boolean defaultValue) {
        return preferences.getBoolean(key, defaultValue);
    }

    public Long getLong(String key, Long defaultValue) {
        return preferences.getLong(key, defaultValue);
    }

    public Float getFloat(String key, Float defaultValue) {
        return preferences.getFloat(key, defaultValue);
    }

    public String getString(String key, String defaultValue) {
        return preferences.getString(key, defaultValue);
    }

    public <T> T getObject(String key, Class<T> cls) {
        try {
            return gson.fromJson(getString(key, null), cls);
        } catch (JsonSyntaxException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public Set<String> getSet(String key) {
        return preferences.getStringSet(key, new HashSet<>());
    }

}
