package com.example.myapplication.api;

import com.example.myapplication.BuildConfig;
import com.example.myapplication.model.notification.request.FindNotificationRequest;
import com.example.myapplication.model.notification.response.GetListNotification;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.protobuf.Any;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiNotificationService {
    String apiUrl = "http://"+ BuildConfig.API_BASE_URL+":8011/";
    Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    ApiNotificationService API_NOTIFICATION_SERVICE = new Retrofit.Builder()
            .baseUrl(apiUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiNotificationService.class);

    @POST("notification/get-list")
    Call<GetListNotification> findNotification(@Header("Authorization") String token,
                                               @Body FindNotificationRequest findNotificationRequest);
}
