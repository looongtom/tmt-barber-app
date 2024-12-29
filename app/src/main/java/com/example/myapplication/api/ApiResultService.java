package com.example.myapplication.api;

import com.example.myapplication.BuildConfig;
import com.example.myapplication.model.result.response.CreateResultResponseData;
import com.example.myapplication.model.result.response.ResultDetailResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
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

    @POST("result/create")
    Call<CreateResultResponseData> createResult( @Header("Authorization") String authorization,
                                                 @Part MultipartBody.Part[] listImg,
                                                 @PartMap Map<String, RequestBody> parameters);

    @Multipart
    @POST("result/create")
    Call<CreateResultResponseData> createResultV2( @Header("Authorization") String authorization,
                                                   @Part List<MultipartBody.Part> listImg,
                                                   @Part("barber_id") RequestBody barberId,
                                                   @Part("user_id") RequestBody userId,
                                                   @Part("booking_id") RequestBody bookingId,
                                                   @Part("description") RequestBody description);


}
