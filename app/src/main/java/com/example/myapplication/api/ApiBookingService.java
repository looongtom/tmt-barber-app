package com.example.myapplication.api;

import com.example.myapplication.BuildConfig;
import com.example.myapplication.model.booking.Booking;
import com.example.myapplication.model.booking.request.CreateBookingRequest;
import com.example.myapplication.model.booking.request.FindBookingRequest;
import com.example.myapplication.model.booking.request.UpdateBookingRequest;
import com.example.myapplication.model.booking.request.UpdateBookingTimeslotRequest;
import com.example.myapplication.model.booking.response.BookingDetail;
import com.example.myapplication.model.booking.response.BookingDetailResponse;
import com.example.myapplication.model.booking.response.GetListBookingResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.protobuf.Any;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiBookingService {
    String apiUrl = "http://"+ BuildConfig.API_BASE_URL+":8002/";
    Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    ApiBookingService API_BOOKING_SERVICE = new Retrofit.Builder()
            .baseUrl(apiUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiBookingService.class);

    @POST("booking/create-kafka")
    Call<Booking> createBooking(@Header("Authorization") String token,
                                @Body CreateBookingRequest createBookingRequest);

    @GET("booking/get-list")
    Call<GetListBookingResponse> getListBooking(@Header("Authorization") String token,
                                                @Query("page") Integer page,
                                                @Query("pageSize") Integer limit);

    @GET("booking/get-by-id")
    Call<BookingDetailResponse> getBookingById(@Header("Authorization") String token,
                                               @Query("id") Integer id);

    @POST("booking/find")
    Call<GetListBookingResponse> findBooking(@Header("Authorization") String token,
                                            @Body FindBookingRequest findBookingRequest);


    @POST("booking/update")
    Call<BookingDetailResponse> updateBooking(@Header("Authorization") String token,
                                              @Body UpdateBookingRequest createBookingRequest);

    @POST("booking/update-booking-service")
    Call<Any> updateBookingService(@Header("Authorization") String token,
                                   @Body UpdateBookingRequest createBookingRequest);

    @POST("booking/update-booking-timeslot")
    Call<Any> updateBookingTimeslotService(@Header("Authorization") String token,
                                   @Body UpdateBookingTimeslotRequest request);

}
