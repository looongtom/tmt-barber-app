package com.example.myapplication.api;

import com.example.myapplication.BuildConfig;
import com.example.myapplication.model.category.response.GetListCategoryResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface ApiServicingService {
    String apiUrl = BuildConfig.API_BASE_URL+":8001/";
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    ApiServicingService API_SERVICING_SERVICE = new Retrofit.Builder()
            .baseUrl(apiUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiServicingService.class);

    @GET("servicing/category/get-list")
    Call<GetListCategoryResponse> getListCategory(@Header("Authorization") String token);
}
