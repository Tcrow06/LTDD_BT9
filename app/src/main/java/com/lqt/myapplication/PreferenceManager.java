package com.lqt.myapplication;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager {

    private static final String PREF_NAME = "UserPreferences";
    private static final String KEY_USER_IMAGE_URL = "user_image_url";
    private SharedPreferences sharedPreferences;

    // Constructor
    public PreferenceManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    // Lưu URL ảnh của người dùng
    public void saveUserImageUrl(String imageUrl) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USER_IMAGE_URL, imageUrl);
        editor.apply();  // Dùng apply() thay vì commit() để không chờ đợi kết quả lưu
    }

    // Lấy URL ảnh của người dùng
    public String getUserImageUrl() {
        return sharedPreferences.getString(KEY_USER_IMAGE_URL, null);
    }

    // Xóa URL ảnh (nếu cần)
    public void clearUserImageUrl() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(KEY_USER_IMAGE_URL);
        editor.apply();
    }
}
