package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.myapplication.model.Account;
import com.example.myapplication.model.TimeSlot;

public class BookingActivity extends AppCompatActivity {
    private TextView tvNameCus, tvNameBarber, tvDate, tvTime;
    private TimeSlot timeSlot;
    private Account account;
    private String queryDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        tvNameCus = findViewById(R.id.txtUsername);
        tvNameBarber = findViewById(R.id.txtStaff);
        tvDate = findViewById(R.id.txtBookingTime);
        tvTime = findViewById(R.id.txtSlot);

        timeSlot= (TimeSlot) getIntent().getSerializableExtra("timeSlot");
        account = (Account) getIntent().getSerializableExtra("account");
        queryDate = getIntent().getStringExtra("queryDate");

        tvNameCus.setText("Customer");
        tvNameBarber.setText(account.getName());
        tvDate.setText(queryDate);
        tvTime.setText(timeSlot.getTimeStart());


    }
}