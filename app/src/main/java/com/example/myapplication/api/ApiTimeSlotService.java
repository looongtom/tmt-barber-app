package com.example.myapplication.api;

import com.example.myapplication.BuildConfig;
import com.example.myapplication.UpdateTimeSlot;
import com.example.myapplication.model.timeslot.TimeSlot;
import com.example.myapplication.model.timeslot.request.CreateTimeSlotRequest;
import com.example.myapplication.model.timeslot.request.FindTimeSlotRequest;
import com.example.myapplication.model.timeslot.request.UpdateStatusTimeSlotRequest;
import com.example.myapplication.model.timeslot.response.FindTimeSlotResponse;
import com.example.myapplication.model.timeslot.response.UpdateTimeSlotResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiTimeSlotService {
    String apiUrl = "http://"+ BuildConfig.API_BASE_URL+":8003/";
    Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    ApiTimeSlotService API_TIME_SLOT_SERVICE = new Retrofit.Builder()
            .baseUrl(apiUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiTimeSlotService.class);

    @POST("timeslot/find")
    Call<FindTimeSlotResponse> findTimeSlot(@Body FindTimeSlotRequest findTimeSlotRequest);

    @POST("timeslot/create-by-list")
    Call<FindTimeSlotResponse> createTimeSlot(@Body CreateTimeSlotRequest createTimeSlotRequest);

    @POST("timeslot/create-or-update")
    Call<UpdateTimeSlotResponse> createOrUpdateTimeSlot(@Header("Authorization") String token,
                                                        @Body UpdateStatusTimeSlotRequest updateTimeSlot);
}
