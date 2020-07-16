package com.genesis.apps.comm.util;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;

import javax.inject.Inject;

import static com.genesis.apps.comm.model.KeyNames.SHARED_PREFERENCES_NAME;


@SuppressLint("ApplySharedPref")
public class PreferenceUtil {

    private SharedPreferences preferences;

    @Inject
    public PreferenceUtil(Application application){
        this.preferences = application.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public void setString(String key, String value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value).commit();
    }

    public void setInt(String key, int value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, value).commit();
    }

    public void setLong(String key, long value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(key, value).commit();
    }

    public void setFloat(String key, float value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putFloat(key, value).commit();
    }

    public void setBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value).commit();
    }

    public String getString(String key, String defaultValue) {
        return preferences.getString(key, defaultValue);
    }

    public long getLong(String key, long defaultValue) {
        return preferences.getLong(key, defaultValue);
    }

    public float getFloat(String key, float defaultValue) {
        return preferences.getFloat(key, defaultValue);
    }

    public int getInt(String key, int defaultValue) {
        return preferences.getInt(key, defaultValue);
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return preferences.getBoolean(key, defaultValue);
    }

    public Map<String, ?> getAll() {
        return preferences.getAll();
    }

    @SuppressLint("CommitPrefEdits")
    public void delete(String key) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(key).commit();
    }

    public void clear() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear().commit();
    }
}
