package com.example.myapplication.fragment;

import static com.example.myapplication.model.account.Account.RoleUser;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.BookingAdapter;
import com.example.myapplication.api.ApiBookingService;
import com.example.myapplication.auth.TokenManager;
import com.example.myapplication.dal.BookingDataSource;
import com.example.myapplication.dal.DatabaseHelper;
import com.example.myapplication.model.booking.Booking;
import com.example.myapplication.model.booking.response.BookingResponse;
import com.example.myapplication.model.booking.response.GetListBookingResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentHistory  extends Fragment {
    BookingAdapter adapter;
    private RecyclerView recyclerView;
    private TokenManager tokenManager ;
    private List<BookingResponse> listBookings;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("UserData", Context.MODE_PRIVATE);
        int roleId = sharedPreferences.getInt("roleId", -1);
        int userId = sharedPreferences.getInt("userId", -1);
        String userName=sharedPreferences.getString("username","");

        if(roleId!=RoleUser){
            return inflater.inflate(R.layout.fragment_history_staff,container,false);
        }

        return inflater.inflate(R.layout.fragment_history,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView=view.findViewById(R.id.rcvListBooking);
        adapter=new BookingAdapter(getContext());
        tokenManager = new TokenManager(getContext());
        listBookings = new ArrayList<>();

        LinearLayoutManager manager=new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);


    }


    @Override
    public void onResume() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("UserData", Context.MODE_PRIVATE);
        int roleId = sharedPreferences.getInt("roleId", -1);
        int userId = sharedPreferences.getInt("userId", -1);
        String userName=sharedPreferences.getString("username","");

        super.onResume();

//        if(roleId!=3){
//            BookingDataSource bookingDataSource = new BookingDataSource(getContext());
//            List<Booking> bookings = bookingDataSource.getBookingByStaffId(getContext(),userId);
//            adapter.setData(listBookings);
//            return;
//        }

//        BookingDataSource bookingDataSource = new BookingDataSource(getContext());
//        List<Booking> bookings = bookingDataSource.getBookingByUserId(getContext(),userId);
//        adapter.setData(listBookings);
            sendApiGetListBooking();
    }

    private void sendApiGetListBooking(){
        String accessToken = tokenManager.getAccessToken();
        ApiBookingService.API_BOOKING_SERVICE.getListBooking(accessToken).enqueue(new Callback<GetListBookingResponse>() {
            @Override
            public void onResponse(Call<GetListBookingResponse> call, Response<GetListBookingResponse> response) {
                if(response.isSuccessful()){
                    List<BookingResponse> bookings = response.body().getData();
                    listBookings = bookings;
                    adapter.setData(listBookings);
                }
            }

            @Override
            public void onFailure(Call<GetListBookingResponse> call, Throwable t) {

            }
        });

    }
}
