package com.example.myapplication.api;

import com.example.myapplication.BuildConfig;
import com.example.myapplication.model.account.response.GetListBarberResponse;
import com.example.myapplication.model.account.response.GetProfileResponse;
import com.example.myapplication.model.account.request.LoginRequest;
import com.example.myapplication.model.account.response.LoginResponse;
import com.example.myapplication.model.hairfast.GenerateHairResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
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

public interface ApiAccountService {
    Interceptor interceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request=chain.request();
            Request.Builder requestBuilder=request.newBuilder();
            requestBuilder.
                    addHeader("Authorization","eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE3MjY3NzM1OTksInN1YiI6IjEiLCJ1c2VybmFtZSI6IjEifQ.1oxpHk50WpzydzXwwUMz4GMIrozPn1m4rTvKoXZ0GHE");
            return chain.proceed(requestBuilder.build());
        }
    };

    OkHttpClient.Builder okBuilder = new OkHttpClient.Builder().addInterceptor(interceptor);
    String apiUrl = "http://"+BuildConfig.API_BASE_URL+":8000/";

    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    ApiAccountService API_ACCOUNT_SERVICE = new Retrofit.Builder()
            .baseUrl(apiUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okBuilder.build())
            .build()
            .create(ApiAccountService.class);

    @POST("auth/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @GET("auth/profile")
    Call<GetProfileResponse> getProfile(@Header("Authorization") String token , @Query("email") String email);

    @GET("barber/get-list")
    Call<GetListBarberResponse> getListBarber();

    @Multipart
    @POST("previewimage/upload")
    Call<GenerateHairResponse> generateHair(@Header("Authorization") String token,
                                            @Part("self_img") MultipartBody.Part selfImg,
                                            @Part("shape_img") MultipartBody.Part shapeImg,
                                            @Part("color_img") MultipartBody.Part colorImg);
}
