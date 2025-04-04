package com.lqt.myapplication.retrofit;

import com.lqt.myapplication.model.ImageUpload;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ServiceAPI {
    @Multipart
    @POST("upfile")
    Call<ImageUpload> upload(@Part MultipartBody.Part avatar);

}