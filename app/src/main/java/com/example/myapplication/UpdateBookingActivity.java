package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.adapter.ChooseServiceRecycleViewAdapter;
import com.example.myapplication.dal.AccountDataSource;
import com.example.myapplication.dal.BookingDataSource;
import com.example.myapplication.dal.BookingDetailDataSource;
import com.example.myapplication.dal.ServiceDataSource;
import com.example.myapplication.dal.TimeSlotDataSource;
import com.example.myapplication.model.Booking;
import com.example.myapplication.model.Service;

import java.util.ArrayList;
import java.util.List;

public class UpdateBookingActivity extends AppCompatActivity {
    private TextView txtUsername, txtStaff, txtBookingTime, txtPrice, txtStatus;
    private EditText txtSlot;
    private RecyclerView recService;
    private Button btnSave, btnChooseService, btDelete, btChooseTimeSlot;
    private Booking booking;
    private ChooseServiceRecycleViewAdapter adapter;
    private Double price = 0.0;
    private List<Service> listService = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_booking);
        initView();

        Intent intent = getIntent();
        Integer bookingId = intent.getIntExtra("bookingId", 0);
        BookingDataSource bookingDataSource = new BookingDataSource(this);
        booking = bookingDataSource.getById(bookingId);

        AccountDataSource accountDataSource = new AccountDataSource(this);
        TimeSlotDataSource timeSlotDataSource = new TimeSlotDataSource(this);

        txtUsername.setText(accountDataSource.getUsernameById(booking.getUserId()));
        txtStaff.setText(accountDataSource.getUsernameById(booking.getBarberId()));
        txtBookingTime.setText(booking.getCreateTime());
        txtSlot.setText(timeSlotDataSource.getTimeSlotById(booking.getSlotId()).getTimeStart() + "  :  " + booking.getTime());
        txtPrice.setText(booking.getPrice() + "");
        txtStatus.setText(booking.getStatus());


        BookingDetailDataSource bookingDetailDataSource = new BookingDetailDataSource(this);
        List<Integer> listIdServices = bookingDetailDataSource.getListServiceByBookingId(booking.getId());
        List<Service> listService = getListService(listIdServices);

        adapter = new ChooseServiceRecycleViewAdapter();
        adapter.setChoose(false);
        adapter.setList(listService);

        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        recService.setLayoutManager(manager);
        recService.setAdapter(adapter);

        btChooseTimeSlot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeSlotDataSource.updateStatusTimeSlot(booking.getSlotId(), "Currently Booked");

                Intent intent = new Intent(getApplicationContext(), UpdateTimeSlot.class);
                intent.putExtra("bookingId", booking.getId());
                startActivity(intent);
            }
        });

        btnChooseService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UpdateChooseService.class);
                intent.putExtra("bookingId", booking.getId());
                startActivity(intent);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookingDataSource db = new BookingDataSource(getApplicationContext());
                booking.setStatus("Đã nhận khách");
                booking.setPrice(price);
                db.updateBooking(booking);
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
    }

    private void showConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to delete?")
                .setTitle("Delete Confirmation")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        BookingDataSource db = new BookingDataSource(getApplicationContext());
                        booking.setStatus("Hủy");
                        db.updateBooking(booking);
                        dialog.dismiss(); // Close the dialog
                        finish();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss(); // Close the dialog
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
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
        txtPrice = findViewById(R.id.tv6);
        txtStatus = findViewById(R.id.txtStatus);
        recService = findViewById(R.id.rcvService);
        btnSave = findViewById(R.id.btSave);
        btnChooseService = findViewById(R.id.btChooseService);
        btDelete = findViewById(R.id.btDelete);
        btChooseTimeSlot = findViewById(R.id.btChooseTimeSlot);
    }

    @Override
    protected void onResume() {
        super.onResume();
        BookingDetailDataSource bookingDetailDataSource = new BookingDetailDataSource(this);
        List<Integer> listIdServices = bookingDetailDataSource.getListServiceByBookingId(booking.getId());
        List<Service> listService = getListService(listIdServices);
        price = 0.0;
        for (Service service: listService) price+=service.getPrice();
        txtPrice.setText(price + "");
        adapter.setList(listService);
        adapter.notifyDataSetChanged();

        BookingDataSource bookingDataSource = new BookingDataSource(this);
        booking.setPrice(price);
        bookingDataSource.updateBooking(booking);

    }
}