package com.lqt.myapplication;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager {

    private static final String PREF_NAME = "user";
    private static final String KEY_USER_IMAGE_URL = "avatar";
    private SharedPreferences sharedPreferences;

    public PreferenceManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void saveUserImageUrl(String imageUrl) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USER_IMAGE_URL, imageUrl);
        editor.apply();
    }

    public String getUserImageUrl() {
        return sharedPreferences.getString(KEY_USER_IMAGE_URL, null);
    }

    public void clearUserImageUrl() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(KEY_USER_IMAGE_URL);
        editor.apply();
    }
}
