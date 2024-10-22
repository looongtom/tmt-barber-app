package com.example.myapplication.api;

import com.example.myapplication.BuildConfig;
import com.example.myapplication.model.result.response.ResultDetailResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface ApiResultService {
    String apiUrl = "http://"+ BuildConfig.API_BASE_URL+":8007/";
    Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    ApiResultService API_RESULT_SERVICE = new Retrofit.Builder()
            .baseUrl(apiUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiResultService.class);

    @GET("result/getResultByBookingId")
    Call<ResultDetailResponse> getResultByBookingId(@Header("Authorization") String token,
                                                    @Query("id") Integer bookingId);
}
