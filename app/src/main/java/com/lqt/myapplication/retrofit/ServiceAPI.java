package com.lqt.myapplication.retrofit;

import android.os.Message;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lqt.myapplication.instants.Const;
import com.lqt.myapplication.model.ImageUpload;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ServiceAPI {

    @Multipart
    @POST("upfile")
    Call<ImageUpload> upload(@Part MultipartBody.Part avatar);

}