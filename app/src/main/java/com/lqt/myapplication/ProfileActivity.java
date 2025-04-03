package com.lqt.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class ProfileActivity extends AppCompatActivity {
    private ImageView profileImage;
    private Button logoutButton;
    private ImageButton btnBack;
    private ImageView avatarImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profileImage = findViewById(R.id.profileImage);
        logoutButton = findViewById(R.id.logoutButton);
        btnBack = findViewById(R.id.btnBack);

        // Quay lại màn hình trước đó
        btnBack.setOnClickListener(v -> finish());

        PreferenceManager preferenceManager = new PreferenceManager(this);
        String imgUrl = preferenceManager.getUserImageUrl();

        if (imgUrl != null && !imgUrl.isEmpty()) {
            Glide.with(this)
                    .load(imgUrl)
                    .placeholder(R.drawable.ic_user) // Ảnh chờ khi tải
                    .error(R.drawable.ic_user) // Ảnh lỗi nếu tải thất bại
                    .into(profileImage);
        }

        // Nhấn vào ảnh đại diện để chọn ảnh mới
        profileImage.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, UploadFileActivity.class);
            startActivity(intent);
        });

        // Xử lý đăng xuất
        logoutButton.setOnClickListener(v -> {
            Toast.makeText(this, "Đăng xuất thành công", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}
