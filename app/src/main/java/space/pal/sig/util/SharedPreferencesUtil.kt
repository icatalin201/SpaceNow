package space.pal.sig.util

import android.app.Application
import android.content.Context
import android.content.SharedPreferences

/**
SpaceNow
Created by Catalin on 12/6/2020
 **/
class SharedPreferencesUtil(
        context: Application
) {

    companion object {
        private const val SPACE_NOW_PREF = "space_now_pref"
    }

    private val sharedPreferences = context.getSharedPreferences(
            SPACE_NOW_PREF, Context.MODE_PRIVATE)

    fun save(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    fun get(key: String, defaultValue: String?): String? {
        return sharedPreferences.getString(key, defaultValue)
    }

    fun save(key: String, value: Int) {
        sharedPreferences.edit().putInt(key, value).apply()
    }

    fun get(key: String, defaultValue: Int): Int {
        return sharedPreferences.getInt(key, defaultValue)
    }

    fun save(key: String, value: Long) {
        sharedPreferences.edit().putLong(key, value).apply()
    }

    fun get(key: String, defaultValue: Long): Long {
        return sharedPreferences.getLong(key, defaultValue)
    }

    fun save(key: String, value: Boolean) {
        sharedPreferences.edit().putBoolean(key, value).apply()
    }

    fun get(key: String, defaultValue: Boolean): Boolean {
        return sharedPreferences.getBoolean(key, defaultValue)
    }

    fun save(key: String, items: Set<String>) {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putStringSet(key, items)
        editor.apply()
    }

    fun get(key: String): MutableSet<String> {
        return sharedPreferences.getStringSet(key, mutableSetOf())!!
    }

    fun remove(key: String) {
        sharedPreferences.edit().remove(key).apply()
    }

}