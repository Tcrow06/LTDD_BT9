package com.lqt.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class ProfileActivity extends AppCompatActivity {
    private ImageView profileImage;
    private Button logoutButton;
    private ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profileImage = findViewById(R.id.profileImage);
        logoutButton = findViewById(R.id.logoutButton);
        btnBack = findViewById(R.id.btnBack);

        btnBack.setOnClickListener(v -> finish());

        PreferenceManager preferenceManager = new PreferenceManager(this);
        String imgUrl = preferenceManager.getUserImageUrl();

        Glide.with(this)
                .load(imgUrl)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .placeholder(R.drawable.ic_user)
                .error(R.drawable.ic_user)
                .into(profileImage);


        profileImage.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, UploadFileActivity.class);
            startActivity(intent);
        });

        logoutButton.setOnClickListener(v -> {
            Toast.makeText(this, "Đăng xuất thành công", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}
