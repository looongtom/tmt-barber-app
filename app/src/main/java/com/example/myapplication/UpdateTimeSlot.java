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
import com.example.myapplication.dal.BookingDataSource;
import com.example.myapplication.dal.TimeSlotDataSource;
import com.example.myapplication.model.Booking;
import com.example.myapplication.model.TimeSlot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
    private TimeSlot currentSlot;
    private Booking booking;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_time_slot);

        initView();
        adapter=new ChooseTimeSlotRecycleViewAdapter(this);
        LinearLayoutManager manager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setLayoutManager(new GridLayoutManager(this,3));
        recyclerView.setAdapter(adapter);
        adapter.setItemListener(this);

        int bookingId=getIntent().getIntExtra("bookingId",0);
        BookingDataSource bookingDataSource=new BookingDataSource(this);
        booking = bookingDataSource.getById(bookingId);
        barberId=booking.getBarberId();


        edtDate.setText(booking.getTime());
        queryDate=edtDate.getText().toString();

        TimeSlotDataSource timeSlotDataSource = new TimeSlotDataSource(UpdateTimeSlot.this);
        timeSlotList =   timeSlotDataSource.getTimeSlotByBarberIdAndDate(barberId,edtDate.getText().toString());
//        adapter.setList(timeSlotList);
        currentSlot=timeSlotDataSource.getTimeSlotById(booking.getSlotId());


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
                        if (mMonth > 8) {
                            date = dayOfMonth+ "-" + (month + 1)+ "-"+year;
                        } else {
                            date = dayOfMonth + "-0" + (month + 1)+ "-"+year;
                        }
                        if(compareDate(date,getToday())){
                            edtDate.setText(date);
                            queryDate = date;
                        }else{
                            Toast.makeText(UpdateTimeSlot.this, "Cannot choose date before today", Toast.LENGTH_SHORT).show();
                            edtDate.setText(getToday());
                            queryDate=getToday();
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
                timeSlotList.clear();
                TimeSlotDataSource timeSlotDataSource = new TimeSlotDataSource(UpdateTimeSlot.this);
                timeSlotList =   timeSlotDataSource.getTimeSlotByBarberIdAndDate(barberId,edtDate.getText().toString());
                if( timeSlotList==null ){
                    timeSlotList=timeSlotDataSource.insertTimeSlotForDate(edtDate.getText().toString(),barberId);
                }
//                adapter.setList(timeSlotList);
            }
        });

        btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isChosen){
                    TimeSlotDataSource timeSlotDataSource = new TimeSlotDataSource(UpdateTimeSlot.this);
                    timeSlotDataSource.updateStatusTimeSlot(currentSlot.getId(),"Available");
                    booking.setSlotId(choosenTimeSlot.getId());
                    booking.setTime(queryDate);
                    BookingDataSource bookingDataSource = new BookingDataSource(UpdateTimeSlot.this);
                    bookingDataSource.updateBooking(booking);
                    timeSlotDataSource.updateStatusTimeSlot(choosenTimeSlot.getId(),"Booked");

                    Intent intent = new Intent(UpdateTimeSlot.this, UpdateBookingActivity.class);
                    intent.putExtra("bookingId",booking.getId());
                    startActivity(intent);
                }else{
                    Toast.makeText(UpdateTimeSlot.this, "Please choose a time slot", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            TimeSlotDataSource timeSlotDataSource = new TimeSlotDataSource(UpdateTimeSlot.this);
            timeSlotDataSource.updateStatusTimeSlot(currentSlot.getId(),"Available");
        }
        return super.onKeyDown(keyCode, event);
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

    private String getToday() {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        String date = "";
        if (mMonth > 8) {
            date = mDay+ "-" + (mMonth + 1)+ "-"+mYear;
        } else {
            date = mDay + "-0" + (mMonth + 1)+ "-"+mYear;
        }
        queryDate = date;
        return date;
    }

    private void initView() {
        recyclerView=findViewById(R.id.recyclerTimeSlot);
        edtDate=findViewById(R.id.eDate);
        btNext=findViewById(R.id.btNext);
    }


    @Override
    public void onItemCLick(View view, int pos) {
//        CardView cardView = view.findViewById(R.id.cardView);
//        TimeSlot timeSlot = adapter.getItem(pos);
//        if (timeSlot.getStatus().equals("Available")) {
//            if (isChosen) {
////                for (CardView card : cardViewList) {
////                    card.setCardBackgroundColor(getResources().getColor(R.color.white));
////                }
////                cardViewList.clear();
//                choosenCardView.setCardBackgroundColor(getResources().getColor(R.color.white));
//            }
//            cardView.setCardBackgroundColor(getResources().getColor(R.color.primary));
////            cardViewList.add(cardView);
//            choosenCardView = cardView;
//            isChosen = true;
//            choosenTimeSlot = timeSlot;
//        }
    }


    @Override
    public void onResume() {
        super.onResume();
        TimeSlotDataSource timeSlotDataSource = new TimeSlotDataSource(this);
        List<TimeSlot> list = (List<TimeSlot>) timeSlotDataSource.getTimeSlotByBarberIdAndDate(barberId,queryDate);
//        adapter.setList(list);
    }
}