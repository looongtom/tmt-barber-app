package com.example.myapplication;

import static com.example.myapplication.model.account.Account.RoleUser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.adapter.ChooseServiceRecycleViewAdapter;
import com.example.myapplication.api.ApiBookingService;
import com.example.myapplication.auth.TokenManager;
import com.example.myapplication.model.booking.response.BookingDetail;
import com.example.myapplication.model.booking.response.BookingDetailResponse;
import com.example.myapplication.model.booking.response.BookingResponse;
import com.example.myapplication.model.booking.response.ServicingResponse;
import com.example.myapplication.model.timeslot.TimeSlot;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewBookingFromNoti extends AppCompatActivity {
    private TextView txtUsername, txtStaff, txtBookingTime, txtPrice, txtStatus;
    private EditText txtSlot;
    private RecyclerView recService;
    private Button btnDetail;
    private BookingResponse booking;
    private BookingDetailResponse bookingDetailResponse;
    private ChooseServiceRecycleViewAdapter adapter;
    private TokenManager tokenManager ;
    private List<ServicingResponse> listService = new ArrayList<>();
    private Boolean isChooseService = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = this.getSharedPreferences("UserData", Context.MODE_PRIVATE);
        int roleId = sharedPreferences.getInt("roleId", -1);
        setContentView(R.layout.activity_view_booking_from_noti);
        initView();
        tokenManager = new TokenManager(this);
        Intent intent = getIntent();
        booking= (BookingResponse) intent.getSerializableExtra("booking");
        sendApiGetBookingDetail(booking.getId());

        setBookingData();

        btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewBookingFromNoti.this, UpdateBookingActivity.class);
                intent.putExtra("booking", booking);
                startActivity(intent);
            }
        });

    }

    private void setBookingData() {
        txtUsername.setText(booking.getCustomerName());
        txtStaff.setText(booking.getBarberName());
        txtBookingTime.setText(booking.getBookedDate());
//        txtSlot.setText(booking.getTimeSlot().getStartTime());
        txtPrice.setText(booking.getPrice()+"");
        txtStatus.setText(booking.getStatus());

        if (booking.getStatus().equals("Canceled") ){
            btnDetail.setVisibility(View.GONE);
            // set txtStatus color to red
            txtStatus.setTextColor(getResources().getColor(R.color.choosen_color));
        }
    }


    private void sendApiGetBookingDetail(Integer bookingId) {
        String accessToken = tokenManager.getAccessToken();
        ApiBookingService.API_BOOKING_SERVICE.getBookingById(accessToken,bookingId).enqueue(new Callback<BookingDetailResponse>() {
            @Override
            public void onResponse(Call<BookingDetailResponse> call, Response<BookingDetailResponse> response) {
                if(response.isSuccessful()){
                    bookingDetailResponse = response.body();

                    adapter = new ChooseServiceRecycleViewAdapter();

                    if(isChooseService){
                        List<ServicingResponse> listServices = (List<ServicingResponse>) getIntent().getSerializableExtra("listServices");
                        listService = listServices;
                    }else {
                        listService = bookingDetailResponse.getBookingDetail().getListServiceStruct();
                    }

                    adapter.setChoose(false);
                    adapter.setList(listService);
                    adapter.notifyDataSetChanged();

                    txtUsername.setText(bookingDetailResponse.getBookingDetail().getCustomerName());
                    txtStaff.setText(bookingDetailResponse.getBookingDetail().getBarberName());
                    txtBookingTime.setText(bookingDetailResponse.getBookingDetail().getBookedDate());
                    txtSlot.setText(bookingDetailResponse.getBookingDetail().getStartTime());
                    txtPrice.setText(bookingDetailResponse.getBookingDetail().getPrice()+"");
                    txtStatus.setText(bookingDetailResponse.getBookingDetail().getStatus());

                    LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
                    recService.setLayoutManager(manager);
                    recService.setAdapter(adapter);
                }else if(response.code()==401) {
                    Toast.makeText(ViewBookingFromNoti.this, "Token is expired", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ViewBookingFromNoti.this, LoginActivity.class);
                    finish();
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<BookingDetailResponse> call, Throwable t) {
                Toast.makeText(ViewBookingFromNoti.this, "error get list booking detail", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void initView() {
        txtUsername = findViewById(R.id.txtUsername);
        txtStaff = findViewById(R.id.txtStaff);
        txtBookingTime = findViewById(R.id.txtBookingTime);
        txtSlot = findViewById(R.id.txtSlot);
        txtPrice = findViewById(R.id.tvPrice);
        txtStatus = findViewById(R.id.txtStatus);
        recService = findViewById(R.id.rcvService);
        btnDetail = findViewById(R.id.btDetail);
    }
}