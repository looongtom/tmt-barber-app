package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.adapter.ChooseTimeSlotRecycleViewAdapter;
import com.example.myapplication.api.ApiBookingService;
import com.example.myapplication.api.ApiTimeSlotService;
import com.example.myapplication.auth.TokenManager;
import com.example.myapplication.model.booking.Booking;
import com.example.myapplication.model.booking.request.UpdateBookingStatusRequest;
import com.example.myapplication.model.booking.request.UpdateBookingTimeslotRequest;
import com.example.myapplication.model.booking.response.BookingResponse;
import com.example.myapplication.model.timeslot.TimeSlot;
import com.example.myapplication.model.timeslot.request.FindTimeSlotRequest;
import com.example.myapplication.model.timeslot.response.FindTimeSlotResponse;
import com.google.protobuf.Any;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateTimeSlot extends AppCompatActivity implements ChooseTimeSlotRecycleViewAdapter.ItemListener {
    private ChooseTimeSlotRecycleViewAdapter adapter;
    private RecyclerView recyclerView;
    private EditText edtDate;
    private Button btNext;
    private Boolean isChosen = false;

    private List<CardView> cardViewList=new ArrayList<>();
    private CardView choosenCardView=null;
    private List<TimeSlot> timeSlotList=new ArrayList<>();
    private TimeSlot choosenTimeSlot=null;
    private String queryDate;
    private int barberId;
    private TimeSlot currentSlot=null;
    private BookingResponse booking;
    private TokenManager tokenManager ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_time_slot);
        booking = (BookingResponse) getIntent().getSerializableExtra("booking");
        currentSlot = (TimeSlot) getIntent().getSerializableExtra("timeSlot");
        initView();

        tokenManager = new TokenManager(this);

        edtDate.setShowSoftInputOnFocus(false);
        edtDate.setText(getToday());

        adapter=new ChooseTimeSlotRecycleViewAdapter(this);
        LinearLayoutManager manager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setLayoutManager(new GridLayoutManager(this,3));
        recyclerView.setAdapter(adapter);
        adapter.setItemListener(this);

        barberId=booking.getBarberId();


        queryDate=edtDate.getText().toString();

        edtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(UpdateTimeSlot.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String date = "";
                        if (dayOfMonth < 10) {
                            date = "0" + dayOfMonth + "-";
                        } else {
                            date = dayOfMonth + "-";
                        }
                        if (month > 8) {
                            date +=  (month + 1) + "-" + year;
                        } else {
                            date += "0" + (month + 1) + "-" + year;
                        }
                        if(compareDate(date,getToday())){
                            edtDate.setText(date);
                            queryDate = date;
                        }else{
                            Toast.makeText(UpdateTimeSlot.this, "Cannot choose date before today", Toast.LENGTH_SHORT).show();
                            edtDate.setText(currentSlot.getDate());
                            queryDate=currentSlot.getDate();
                        }

                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        edtDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                sendApiGetListTimeslot(new FindTimeSlotRequest(barberId, edtDate.getText().toString(),""));
            }
        });

        btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isChosen){
                    sendApiUpdateBooking();
                }else{
                    Toast.makeText(UpdateTimeSlot.this, "Please choose a time slot", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {

        }
        return super.onKeyDown(keyCode, event);
    }

    private String getToday() {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        String date = "";
        if (mDay < 10) {
            date = "0" + mDay + "-";
        } else {
            date = mDay + "-";
        }
        if (mMonth > 8) {
            date +=  (mMonth + 1) + "-" + mYear;
        } else {
            date += "0" + (mMonth + 1) + "-" + mYear;
        }
        queryDate = date;
        return date;
    }

    public boolean compareDate(String str1, String str2) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date date1 = sdf.parse(str1);
            Date date2 = sdf.parse(str2);

            if(date1.compareTo(date2) < 0) {
                //before
                return false;
            } else {
//                after
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void sendApiUpdateBooking(){
        ApiBookingService.API_BOOKING_SERVICE.updateBookingTimeslotService(tokenManager.getAccessToken(),
                new UpdateBookingTimeslotRequest(booking.getId(),choosenTimeSlot.getId())).enqueue(new Callback<Any>() {
            @Override
            public void onResponse(Call<Any> call, Response<Any> response) {
                if(response.isSuccessful()){
                    Toast.makeText(UpdateTimeSlot.this, "Update booking successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(UpdateTimeSlot.this,UpdateBookingActivity.class);
                    intent.putExtra("booking",booking);
                    intent.putExtra("timeSlot", new TimeSlot(choosenTimeSlot.getId(),booking.getTimeSlot().getStartTime(),booking.getTimeSlot().getBookedDate(),"Currently Booked",booking.getBarberId()));
                    finish();
                    startActivity(intent);
                }else{
                    Toast.makeText(UpdateTimeSlot.this, "Update booking failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Any> call, Throwable t) {
                Toast.makeText(UpdateTimeSlot.this, "Update booking failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendApiGetListTimeslot(FindTimeSlotRequest request) {
        ApiTimeSlotService.API_TIME_SLOT_SERVICE.findTimeSlot(request).enqueue(new Callback<FindTimeSlotResponse>() {
            @Override
            public void onResponse(Call<FindTimeSlotResponse> call, Response<FindTimeSlotResponse> response) {
                if (response.isSuccessful()) {
                    FindTimeSlotResponse findTimeSlotResponse = response.body();
                    List<TimeSlot> list =findTimeSlotResponse.getData();
                    timeSlotList = list;
                    for (TimeSlot timeSlot : timeSlotList) {
                        if (timeSlot.getId()==currentSlot.getId()) {
                            timeSlot.setStatus("Currently Booked");
                            break;
                        }
                    }
                    if (timeSlotList == null) {
                        Toast.makeText(UpdateTimeSlot.this, "No time slot available", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        adapter.setList(timeSlotList);
                    }
                }else if(response.code()==401){
                    startActivity(new Intent(UpdateTimeSlot.this, LoginActivity.class));
                    finish();
                }
                else{
                    Toast.makeText(UpdateTimeSlot.this,"error when get list time slot",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FindTimeSlotResponse> call, Throwable t) {
                Toast.makeText(UpdateTimeSlot.this,"error when get list time slot",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView() {
        recyclerView=findViewById(R.id.recyclerTimeSlot);
        edtDate=findViewById(R.id.eDate);
        btNext=findViewById(R.id.btNext);
        sendApiGetListTimeslot(new FindTimeSlotRequest(barberId, currentSlot.getDate(),""));
    }

    @Override
    public void onItemCLick(View view, int pos) {
        CardView cardView = view.findViewById(R.id.cardView);
        TimeSlot timeSlot = adapter.getItem(pos);
        if (timeSlot.getStatus().equals("Available")) {
            if (isChosen) {
                for (CardView card : cardViewList) {
                    card.setCardBackgroundColor(getResources().getColor(R.color.white));
                }
                cardViewList.clear();
                choosenCardView.setCardBackgroundColor(getResources().getColor(R.color.white));
            }
            cardView.setCardBackgroundColor(getResources().getColor(R.color.primary));
            cardViewList.add(cardView);
            choosenCardView = cardView;
            isChosen = true;
            choosenTimeSlot = timeSlot;
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        if (timeSlotList ==null || timeSlotList.size() == 0) {
            sendApiGetListTimeslot(new FindTimeSlotRequest(barberId, edtDate.getText().toString(),""));
            adapter.setList(timeSlotList);
        }
    }
}