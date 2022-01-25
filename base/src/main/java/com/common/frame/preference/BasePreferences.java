package com.common.frame.preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;


import com.common.frame.BaseApp;
import com.common.frame.utils.Utils;

import java.util.Map;

/**
 * @author lst
 * @desc desc
 * @date 2021/6/18
 */
public abstract class BasePreferences {
    protected SharedPreferences mPreference;

    public BasePreferences() {
        if (Utils.isEmpty(getSPFileName())) {
            mPreference = PreferenceManager.getDefaultSharedPreferences(BaseApp.sApplication);
        } else {
            mPreference = BaseApp.sApplication.getSharedPreferences(getSPFileName(), Context.MODE_PRIVATE);
        }
    }

    protected abstract String getSPFileName();

    public void setString(String key, String value) {
        SharedPreferences.Editor editor = mPreference.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getString(String key, String defValue) {
        return mPreference.getString(key, defValue);
    }

    public String getString(String key) {
        return mPreference.getString(key, "");
    }

    public void setBoolean(String key, boolean bool) {
        SharedPreferences.Editor editor = mPreference.edit();
        editor.putBoolean(key, bool);
        editor.apply();
    }

    public Boolean getBoolean(String key, boolean defBool) {
        return mPreference.getBoolean(key, defBool);
    }

    public void setLong(String key, long value) {
        SharedPreferences.Editor editor = mPreference.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    public long getLong(String key, long defValue) {
        return mPreference.getLong(key, defValue);
    }

    public void setInt(String key, int value) {
        SharedPreferences.Editor editor = mPreference.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public int getInt(String key, int defaultVal) {
        return mPreference.getInt(key, defaultVal);
    }

    public void setFloat(String key, float value) {
        SharedPreferences.Editor editor = mPreference.edit();
        editor.putFloat(key, value);
        editor.apply();
    }

    public float getFloat(String key, float defaultVal) {
        return mPreference.getFloat(key, defaultVal);
    }

    public void remove(String key) {
        if (!TextUtils.isEmpty(key) && mPreference.contains(key)) {
            SharedPreferences.Editor editor = mPreference.edit();
            editor.remove(key);
            editor.apply();
        }
    }

    public boolean contains(String key) {
        return mPreference.contains(key);
    }

    public void clearAll() {
        SharedPreferences.Editor editor = mPreference.edit();
        editor.clear();
        editor.apply();
    }

    public Map<String, ?> getAll() {
        return mPreference.getAll();
    }
}
