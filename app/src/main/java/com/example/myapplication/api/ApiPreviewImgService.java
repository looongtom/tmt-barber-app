package com.example.myapplication.api;

import com.example.myapplication.BuildConfig;
import com.example.myapplication.model.hairfast.request.RequestGetListGeneratedResult;
import com.example.myapplication.model.hairfast.response.DetailHairFastResponse;
import com.example.myapplication.model.hairfast.response.GetListGeneratedResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiPreviewImgService {
    String apiUrl = "http://"+BuildConfig.API_BASE_URL+":8005/";
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS) // Connection timeout
            .readTimeout(60, TimeUnit.SECONDS)   // Read timeout
            .writeTimeout(60, TimeUnit.SECONDS)  // Write timeout
            .build();

    ApiPreviewImgService apiService = new Retrofit.Builder()
            .baseUrl(apiUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
            .create(ApiPreviewImgService.class);

    @Multipart
    @POST("previewimage/upload")
    Call<Void> generateHair(@Header("Authorization") String token,
                                            @Part MultipartBody.Part selfImg,
                                            @Part MultipartBody.Part shapeImg,
                                            @Part MultipartBody.Part colorImg);

    @POST("previewimage/get-list")
    Call<GetListGeneratedResult> getListGeneratedResult(@Header("Authorization") String token,
                                                        @Body RequestGetListGeneratedResult request);

    @GET("previewimage/get-by-id")
    Call<DetailHairFastResponse> getDetailHairFast(@Header("Authorization") String token,
                                                   @Query("id") Integer id);
}
