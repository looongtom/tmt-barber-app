package com.example.myapplication.api;

import com.example.myapplication.BuildConfig;
import com.example.myapplication.model.booking.Booking;
import com.example.myapplication.model.booking.request.CreateBookingRequest;
import com.example.myapplication.model.booking.response.GetListBookingResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiBookingService {
    String apiUrl = "http://"+ BuildConfig.API_BASE_URL+":8002/";
    Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    ApiBookingService API_BOOKING_SERVICE = new Retrofit.Builder()
            .baseUrl(apiUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiBookingService.class);

    @POST("booking/create")
    Call<Booking> createBooking(@Header("Authorization") String token,
                                @Body CreateBookingRequest createBookingRequest);

    @GET("booking/get-list")
    Call<GetListBookingResponse> getListBooking(@Header("Authorization") String token);

}
