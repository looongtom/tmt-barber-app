package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.adapter.ChooseServiceRecycleViewAdapter;
import com.example.myapplication.api.ApiBookingService;
import com.example.myapplication.api.ApiTimeSlotService;
import com.example.myapplication.auth.TokenManager;
import com.example.myapplication.dal.ServiceDataSource;
import com.example.myapplication.model.Service;
import com.example.myapplication.model.booking.request.CreateBookingRequest;
import com.example.myapplication.model.booking.request.UpdateBookingRequest;
import com.example.myapplication.model.booking.response.BookingDetail;
import com.example.myapplication.model.booking.response.BookingDetailResponse;
import com.example.myapplication.model.booking.response.BookingResponse;
import com.example.myapplication.model.booking.response.ServicingResponse;
import com.example.myapplication.model.timeslot.TimeSlot;
import com.example.myapplication.model.timeslot.request.UpdateStatusTimeSlotRequest;
import com.example.myapplication.model.timeslot.response.UpdateTimeSlotResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateBookingActivity extends AppCompatActivity {
    private TextView txtUsername, txtStaff, txtBookingTime, txtPrice, txtStatus;
    private EditText txtSlot;
    private RecyclerView recService;
    private Button btnSave, btnChooseService, btDelete, btChooseTimeSlot,btChooseImage;
    private BookingResponse booking;
    private BookingDetailResponse bookingDetailResponse;
    private ChooseServiceRecycleViewAdapter adapter;
    private Integer price = 0;
    private List<ServicingResponse> listService = new ArrayList<>();
    private TokenManager tokenManager ;
    private Boolean isChooseService = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_booking);
        initView();
        tokenManager = new TokenManager(this);
        bookingDetailResponse = new BookingDetailResponse();

        Intent intent = getIntent();
        booking= (BookingResponse) intent.getSerializableExtra("booking");
        sendApiGetBookingDetail(booking.getId());

        btChooseTimeSlot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                timeSlotDataSource.updateStatusTimeSlot(booking.getSlotId(), "Currently Booked");
                updateStatusTimeslot();

            }
        });

        btnChooseService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isChooseService = true;
                Intent intent = new Intent(getApplicationContext(), UpdateChooseService.class);
                intent.putExtra("booking",booking);
                finish();
                startActivity(intent);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                booking.setStatus("Đã nhận khách");
//                booking.setPrice(price);
                finish();
            }
        });

//        display dialog after click btDelete to confirm delete
        btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmationDialog();
            }
        });

        btChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                open activity to UploadImage
                Intent intent = new Intent(getApplicationContext(), UploadImageV2.class);
                intent.putExtra("bookingId", booking.getId());

                startActivity(intent);
            }
        });
    }

        private void showConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Bạn có chắc huỷ lịch không ?")
                .setTitle("Xác nhận huỷ lịch")
                .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        booking.setStatus("Hủy");
                        dialog.dismiss(); // Close the dialog
                        finish();
                    }
                })
                .setNegativeButton("Quay lại", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss(); // Close the dialog
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void updateStatusTimeslot(){
        String accessToken = tokenManager.getAccessToken();
        ApiTimeSlotService.API_TIME_SLOT_SERVICE.createOrUpdateTimeSlot(accessToken,new UpdateStatusTimeSlotRequest(booking.getTimeSlotId(),"Currently Booked")).enqueue(new Callback<UpdateTimeSlotResponse>() {
            @Override
            public void onResponse(Call<UpdateTimeSlotResponse> call, Response<UpdateTimeSlotResponse> response) {
                if(response.isSuccessful()){
                    UpdateTimeSlotResponse timeSlot = response.body();

                    Intent intent = new Intent(getApplicationContext(), UpdateTimeSlot.class);
                    intent.putExtra("booking", booking);
                    intent.putExtra("timeSlot", timeSlot.getData());
                    finish();
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<UpdateTimeSlotResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Fail to update time slot", Toast.LENGTH_SHORT).show();
            }
        });
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
                    Toast.makeText(UpdateBookingActivity.this, "Token is expired", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BookingDetailResponse> call, Throwable t) {
                Toast.makeText(UpdateBookingActivity.this, "error get list booking detail", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendApiUpdateBooking(UpdateBookingRequest updateBooking){
        String accessToken = tokenManager.getAccessToken();
        ApiBookingService.API_BOOKING_SERVICE.updateBooking(accessToken,updateBooking).enqueue(new Callback<BookingDetailResponse>() {
            @Override
            public void onResponse(Call<BookingDetailResponse> call, Response<BookingDetailResponse> response) {
                if(response.isSuccessful()){
                    bookingDetailResponse = response.body();

                    adapter = new ChooseServiceRecycleViewAdapter();
                    listService = bookingDetailResponse.getBookingDetail().getListServiceStruct();

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
                    Toast.makeText(UpdateBookingActivity.this, "Token is expired", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BookingDetailResponse> call, Throwable t) {
                Toast.makeText(UpdateBookingActivity.this, "error get list booking detail", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private List<Service> getListService(List<Integer> listIdService) {
        List<Service> listService = new ArrayList<>();
        ServiceDataSource db = new ServiceDataSource(getApplicationContext());
        for (Integer id : listIdService) {
            listService.add(db.getById(id));
        }
        return listService;
    }

    private void initView() {
        txtUsername = findViewById(R.id.txtUsername);
        txtStaff = findViewById(R.id.txtStaff);
        txtBookingTime = findViewById(R.id.txtBookingTime);
        txtSlot = findViewById(R.id.txtSlot);
        txtPrice = findViewById(R.id.tvPrice);
        txtStatus = findViewById(R.id.txtStatus);
        recService = findViewById(R.id.rcvService);
        btnSave = findViewById(R.id.btSave);
        btnChooseService = findViewById(R.id.btChooseService);
        btDelete = findViewById(R.id.btDelete);
        btChooseTimeSlot = findViewById(R.id.btChooseTimeSlot);
        btChooseImage = findViewById(R.id.btChooseImage);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sendApiGetBookingDetail(booking.getId());
//        List<ServicingResponse> listService=bookingDetailResponse.getListServiceStruct();
//        price = 0;
//        for (ServicingResponse service: listService) price+=service.getPrice();
//        txtPrice.setText(price + "");
//        adapter.setList(listService);
//        adapter.notifyDataSetChanged();
//
//        booking.setPrice(price);
//        bookingDataSource.updateBooking(booking);

    }
}