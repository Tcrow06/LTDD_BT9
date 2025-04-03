package com.lqt.myapplication;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.IOException;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.lqt.myapplication.instants.Const;
import com.lqt.myapplication.model.ImageUpload;
import com.lqt.myapplication.retrofit.RetrofitClient;
import com.lqt.myapplication.retrofit.ServiceAPI;

import java.io.File;
import java.util.List;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadFileActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView uploadImagePreview;
    private Uri imageUri;
    private ServiceAPI serviceAPI;
    private ProgressDialog progressDialog;

    Button btnChooseFile, btnUploadImages;

    public static final int MY_REQUEST_CODE = 100;
    private Uri mUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_file);

        uploadImagePreview = findViewById(R.id.imgChoose);
        btnChooseFile = findViewById(R.id.btnChoose);
        btnUploadImages = findViewById(R.id.btnUpload);


        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Đang tải lên...");
        progressDialog.setCancelable(false);


        btnChooseFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        // Xử lý khi ấn nút "Upload images"
        btnUploadImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageUri != null) {
                    uploadImage(imageUri, "image");
                } else {
                    Toast.makeText(UploadFileActivity.this, "Vui lòng chọn một ảnh trước!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        PreferenceManager preferenceManager = new PreferenceManager(this);
        String imgUrl = preferenceManager.getUserImageUrl();

        if (imgUrl != null) {
            Glide.with(this).load(imgUrl).into(uploadImagePreview);
        }
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                uploadImagePreview.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void uploadImage(Uri imageUri, String imageKey) {
        progressDialog.show();
        Log.e("DEBUG", "URI nhận được: " + imageUri.toString());
        String imagePath = RealPathUtil.getFilePathFromURI(this, imageUri); // Lấy đường dẫn thực của ảnh
        File imageFile = new File(imagePath);

        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), imageFile);
        MultipartBody.Part body = MultipartBody.Part.createFormData("avatar", imageFile.getName(), requestFile);

        serviceAPI = RetrofitClient.getRetrofitInstance().create(ServiceAPI.class);
        // Gọi API upload ảnh
        Call<ImageUpload> call = serviceAPI.upload(body);
        call.enqueue(new Callback<ImageUpload>() {
            @Override
            public void onResponse(Call<ImageUpload> call, Response<ImageUpload> response) {
                progressDialog.dismiss();
                if (response.isSuccessful() && response.body() != null) {
                    ImageUpload imageUpload = response.body();
                    String imgUrl =imageUpload.getAvatar();

                    PreferenceManager preferenceManager = new PreferenceManager(UploadFileActivity.this);

                    preferenceManager.saveUserImageUrl(imgUrl);

                    Glide.with(UploadFileActivity.this).load(imgUrl).into(uploadImagePreview);

                    Intent intent = new Intent(UploadFileActivity.this, ProfileActivity.class);
                    startActivity(intent);

                } else {
                    Toast.makeText(UploadFileActivity.this, "Upload thất bại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ImageUpload> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(UploadFileActivity.this, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }

}
