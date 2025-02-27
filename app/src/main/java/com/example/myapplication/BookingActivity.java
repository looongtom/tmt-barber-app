package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.adapter.ChooseServiceRecycleViewAdapter;
import com.example.myapplication.dal.BookingDataSource;
import com.example.myapplication.dal.DatabaseHelper;
import com.example.myapplication.dal.ServiceDataSource;
import com.example.myapplication.model.Account;
import com.example.myapplication.model.Booking;
import com.example.myapplication.model.Service;
import com.example.myapplication.model.TimeSlot;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class BookingActivity extends AppCompatActivity implements ChooseServiceRecycleViewAdapter.ItemListener {
    private TextView tvNameCus, tvNameBarber, tvDate, tvTime,tvPrice;
    private TimeSlot timeSlot;
    private Account account;
    private String queryDate;
    private Integer idBooking;
    private Button btBack;
    private RecyclerView recyclerView;
    private ChooseServiceRecycleViewAdapter adapter;
    private DatabaseHelper db;
    private List<Service> listService=new ArrayList<>();
    private Double totalPrice;
    private Booking booking;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        SharedPreferences sharedPreferences = getApplication().getSharedPreferences("UserData", Context.MODE_PRIVATE);
        int roleId = sharedPreferences.getInt("roleId", -1);
        int userId = sharedPreferences.getInt("userId", -1);
        String userName=sharedPreferences.getString("username","");

        tvNameCus = findViewById(R.id.txtUsername);
        tvNameBarber = findViewById(R.id.txtStaff);
        tvDate = findViewById(R.id.txtBookingTime);
        tvTime = findViewById(R.id.txtSlot);
        recyclerView=findViewById(R.id.rcvService);
        btBack=findViewById(R.id.btBack);
        tvPrice=findViewById(R.id.tv6);

        timeSlot= (TimeSlot) getIntent().getSerializableExtra("timeSlot");
        account = (Account) getIntent().getSerializableExtra("account");
        queryDate = getIntent().getStringExtra("queryDate");
        Set<Integer> listIdService=(Set<Integer>) getIntent().getSerializableExtra("listIdService");
        idBooking=getIntent().getIntExtra("idBooking",-1);
        BookingDataSource bookingDataSource=new BookingDataSource(this);
        booking=bookingDataSource.getById(idBooking);

        listService=getListService(listIdService);
        totalPrice=getTotalPrice(listService);
        booking.setPrice(totalPrice);
        //update price in booking
        bookingDataSource.updateBookingPrice(booking.getId(),totalPrice);


        tvPrice.setText(booking.getPrice().toString());

        tvNameCus.setText(userName);
        tvNameBarber.setText(account.getName());
        tvDate.setText(queryDate);
        tvTime.setText(timeSlot.getTimeStart() + "   :   " + booking.getTime());

        adapter = new ChooseServiceRecycleViewAdapter();
        adapter.setChoose(false);

        adapter.setList(listService);
        db=new DatabaseHelper(this);

        LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookingActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    private List<Service> getListService(Set<Integer> listIdService) {
        List<Service> listService=new ArrayList<>();
        ServiceDataSource db=new ServiceDataSource(this);
        for (Integer id:listIdService){
            listService.add(db.getById(id));
        }
        return listService;
    }

    private Double getTotalPrice(List<Service> listService){
        Double totalPrice=0.0;
        for (Service service:listService){
            totalPrice+=service.getPrice();
        }
        return totalPrice;
    }

    @Override
    public void onItemClick(View view, int pos) {

    }
}