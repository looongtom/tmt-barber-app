package com.example.myapplication.api;

import com.example.myapplication.BuildConfig;
import com.example.myapplication.model.hairfast.GenerateHairResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiPreviewImgService {
    String apiUrl = "http://"+BuildConfig.API_BASE_URL+":8085/";
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    ApiPreviewImgService apiService = new Retrofit.Builder()
            .baseUrl(apiUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiPreviewImgService.class);

    @Multipart
    @POST("previewimage/upload")
    Call<GenerateHairResponse> generateHair(@Header("Authorization") String token,
                                            @Part MultipartBody.Part selfImg,
                                            @Part MultipartBody.Part shapeImg,
                                            @Part MultipartBody.Part colorImg);
}
